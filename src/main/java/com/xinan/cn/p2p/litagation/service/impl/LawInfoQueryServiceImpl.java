package com.xinan.cn.p2p.litagation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinan.cn.common.bean.RequestResult;
import com.xinan.cn.common.bean.StatusCode;
import com.xinan.cn.common.mapper.fd.user.FDUserMapper;
import com.xinan.cn.common.service.fd.asset.intf.OpenLoanService;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.litagation.bean.LawLoanerInfo;
import com.xinan.cn.p2p.litagation.bean.entities.CasesLoanerInfo;
import com.xinan.cn.p2p.litagation.bean.nifa.*;
import com.xinan.cn.p2p.litagation.constant.LawConstant;
import com.xinan.cn.p2p.litagation.service.intf.LawInfoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LawInfoQueryServiceImpl implements LawInfoQueryService, LawConstant {

    @Autowired
    private OpenLoanService openLoanService;

    @Autowired
    private FDUserMapper fdUserMapper;


    @Override
    public boolean verifyAllUnclearLawInfo(long time) throws Exception {
        // 1、请求 open-asset 获取所有未结清人员信息
        boolean flag = true;
        int page = 1;
        int count = 0;
        while (count <= COUNT_LIMIT) {
            Map<String, Object> param = new HashMap<>();
            param.put(LawLoanQueryPage.BEGIN, (page - 1) * (LawLoanQueryPage.MAX_SIZE));
            param.put(LawLoanQueryPage.END, LawLoanQueryPage.MAX_SIZE);
            RequestResult requestResult = openLoanService.queryAllUnclearLoanerInfoPage(param);
            if (requestResult.getCode() == StatusCode.CODE_OK && StringUtils.isNotBlank(requestResult.getData())) {
                try {
                    handleUnclearLawInfo(requestResult);
                    page++;
                } catch (Exception e) {
                    count++;
                }
            } else if (requestResult.getCode() == StatusCode.CODE_OK && StringUtils.isBlank(requestResult.getData())) {
                break;
            } else {
                count++;
            }
            TimeUnit.SECONDS.sleep(1);
        }
        if (count > COUNT_LIMIT) {
            flag = false;
        }
        return flag;
    }

    private void handleUnclearLawInfo(RequestResult requestResult) throws Exception {
        String data = requestResult.getData();
        List<LawLoanerInfo> lawLoanerInfos = JSONObject.parseArray(data, LawLoanerInfo.class);
        List<CasesLoanerInfo> insertCases = new ArrayList<>();
        List<CasesLoanerInfo> updateNaturalCases = new ArrayList<>();
        List<CasesLoanerInfo> updateCompanyCases = new ArrayList<>();

        // 企业
        List<LawLoanerInfo> companyLoanerList = lawLoanerInfos.stream().filter(lawLoanerInfo -> LawLoanerTypeConst.COMPANY.equals(lawLoanerInfo.getLoanerType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(companyLoanerList)) {
            handleCompany(insertCases, updateCompanyCases, companyLoanerList);
        }
        // 个人
        List<LawLoanerInfo> naturalLoanerList = lawLoanerInfos.stream().filter(lawLoanerInfo -> LawLoanerTypeConst.NATURAL.equals(lawLoanerInfo.getLoanerType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(naturalLoanerList)) {
            handleNatural(insertCases, updateNaturalCases, naturalLoanerList);
        }

        // 入库
        fdUserMapper.batchInsert(insertCases);
        fdUserMapper.batchUpdateNaturals(updateNaturalCases);
        fdUserMapper.batchUpdateCompanys(updateCompanyCases);
    }

    private void handleNatural(List<CasesLoanerInfo> insertCases, List<CasesLoanerInfo> updateNaturalCases, List<LawLoanerInfo> naturalLoanerList) throws Exception {
        List<LawLoanerInfo> queryNaturalLoaners = fdUserMapper.batchQueryNatrualLoaner(naturalLoanerList);
        List<LawLoanerInfo> validNaturalList = queryNaturalLoaners.stream()
                .filter(queryNaturalLoaner -> DateUtil.isLatestDate(queryNaturalLoaner.getLastRequestDate(), DAYS_LIMIT))
                .collect(Collectors.toList());
        naturalLoanerList.removeAll(queryNaturalLoaners);
        List<CasesLoanerInfo> insertNaturalCases = sendRequestNatural(naturalLoanerList);
        insertCases.addAll(insertNaturalCases);
        queryNaturalLoaners.removeAll(validNaturalList);
        updateNaturalCases = sendRequestNatural(queryNaturalLoaners);
    }

    private List<CasesLoanerInfo> sendRequestNatural(List<LawLoanerInfo> lawLoanerInfoList) throws Exception {
        List<CasesLoanerInfo> naturalCasesList = new ArrayList<>();
        for (LawLoanerInfo naturalInfo : lawLoanerInfoList) {
            NaturalQueryRequest naturalQueryRequest = new NaturalQueryRequest();
            naturalQueryRequest.setName(naturalInfo.getLoanerName());
            naturalQueryRequest.setCardNo(naturalInfo.getIdentityNo());
            CasesLoanerInfo casesLoanerInfo = sendRequest(naturalQueryRequest, naturalInfo);
            naturalCasesList.add(casesLoanerInfo);
        }
        return naturalCasesList;
    }

    private void handleCompany(List<CasesLoanerInfo> insertCases, List<CasesLoanerInfo> updateCompanyCases, List<LawLoanerInfo> companyLoanerList) throws Exception {
        List<LawLoanerInfo> queryCompanyLoaners = fdUserMapper.batchQueryCompanyLoaner(companyLoanerList);
        List<LawLoanerInfo> validCompanyList = queryCompanyLoaners.stream()
                .filter(queryCompanyLoaner -> DateUtil.isLatestDate(queryCompanyLoaner.getLastRequestDate(), DAYS_LIMIT))
                .collect(Collectors.toList());
        companyLoanerList.removeAll(queryCompanyLoaners);
        List<CasesLoanerInfo> insertCompanyCases = sendRequestCompany(companyLoanerList);
        insertCases.addAll(insertCompanyCases);
        queryCompanyLoaners.removeAll(validCompanyList);
        updateCompanyCases = sendRequestCompany(queryCompanyLoaners);
    }

    private List<CasesLoanerInfo> sendRequestCompany(List<LawLoanerInfo> lawLoanerInfoList) throws Exception {
        List<CasesLoanerInfo> companyCasesList = new ArrayList<>();
        for (LawLoanerInfo companyInfo : lawLoanerInfoList) {
            OrgQueryRequest orgQueryRequest = new OrgQueryRequest();
            orgQueryRequest.setName(companyInfo.getLoanerName());
            CasesLoanerInfo casesLoanerInfo = sendRequest(orgQueryRequest, companyInfo);
            companyCasesList.add(casesLoanerInfo);
        }
        return companyCasesList;
    }

    private CasesLoanerInfo sendRequest(LawQueryBasic lawQueryBasic, LawLoanerInfo loanerInfo) throws Exception {
        lawQueryBasic.setAppKey(LawRequestConst.APP_KEY);
        lawQueryBasic.setIndustrType(LawRequestConst.INDUSTR_TYPE);
        lawQueryBasic.setProductType(LawRequestConst.PRODUCT_TYPE);
        lawQueryBasic.setTransactionNo(UUID.randomUUID().toString().replace("-", ""));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String clientTime = simpleDateFormat.format(date);
        lawQueryBasic.setClientTime(clientTime);

        String reponseStr = "";
        if (lawQueryBasic instanceof OrgQueryRequest) {
            OrgQueryRequest orgQueryRequest = (OrgQueryRequest) lawQueryBasic;
            reponseStr = LawQueryNifaHelper.sendPost(LawRequestConst.ORG_PATH, orgQueryRequest);
        } else if (lawQueryBasic instanceof NaturalQueryRequest) {
            NaturalQueryRequest naturalQueryRequest = (NaturalQueryRequest) lawQueryBasic;
            reponseStr = LawQueryNifaHelper.sendPost(LawRequestConst.NATRUAL_PATH, naturalQueryRequest);
        }

        if (StringUtils.isNotBlank(reponseStr)) {
            LawResultResponse lawResultResponse = JSONObject.parseObject(reponseStr, LawResultResponse.class);
            CasesLoanerInfo casesLoanerInfo = new CasesLoanerInfo();
            casesLoanerInfo.setName(loanerInfo.getLoanerName());
            casesLoanerInfo.setUserType(Integer.parseInt(loanerInfo.getLoanerType()));
            casesLoanerInfo.setCardType(Integer.parseInt(loanerInfo.getIdentityType()));
            casesLoanerInfo.setCardId(loanerInfo.getIdentityNo());
            casesLoanerInfo.setUpdatedAt(new Date());
            if (ZERO.equals(lawResultResponse.getCode()) && ZERO.equals(lawResultResponse.getResult())) {
                Map dataMap = (Map) (JSON.parse(lawResultResponse.getData()));
                LawDetailResponse lawDetail = JSONObject.parseObject(MapUtils.getString(dataMap, LawResponseConst.COUNT), LawDetailResponse.class);
                BeanUtils.copyProperties(casesLoanerInfo, lawDetail);
            } else if (ZERO.equals(lawResultResponse.getCode()) && ONE.equals(lawResultResponse.getResult())) {
                // 未查询到
            } else {
                // 异常查询5次
                log.error(lawResultResponse.getCode() + "," + lawResultResponse.getMessage());
                return null;
            }
            return casesLoanerInfo;
        } else {
            throw new RuntimeException("查询中互金系统，无返回内容");
        }
    }

}
