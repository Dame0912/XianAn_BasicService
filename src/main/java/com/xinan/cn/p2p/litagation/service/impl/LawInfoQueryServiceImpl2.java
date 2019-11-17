package com.xinan.cn.p2p.litagation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinan.cn.common.bean.RequestResult;
import com.xinan.cn.common.bean.StatusCode;
import com.xinan.cn.common.mapper.fd.user.FDCasesRequestRecordMapper;
import com.xinan.cn.common.mapper.fd.user.FDUserMapper;
import com.xinan.cn.common.service.fd.asset.intf.OpenLoanService;
import com.xinan.cn.common.utils.AppException;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryRequest;
import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryResponse;
import com.xinan.cn.p2p.litagation.bean.dto.LawLoanerBasicInfo;
import com.xinan.cn.p2p.litagation.bean.entities.CasesLoanerInfo;
import com.xinan.cn.p2p.litagation.bean.entities.CasesRequestRecord;
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

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LawInfoQueryServiceImpl2 implements LawInfoQueryService, LawConstant {

    @Autowired
    private OpenLoanService openLoanService;
    @Autowired
    private FDUserMapper fdUserMapper;
    @Autowired
    private FDCasesRequestRecordMapper casesRequestRecordMapper;


    @Override
    public boolean verifyAllUnclearLawInfo(long time) {
        log.info("LawInfoQueryServiceImpl.verifyAllUnclearLawInfo 执行开始...");
        // 1、请求 open-asset 获取所有未结清人员信息
        boolean flag = true;
        int page = 1;
        int count = 0;
        while (count < COUNT_LIMIT) {
            Map<String, Object> param = new HashMap<>();
            param.put(LawLoanQueryPage.BEGIN, (page - 1) * (LawLoanQueryPage.MAX_SIZE));
            param.put(LawLoanQueryPage.END, LawLoanQueryPage.MAX_SIZE);
            RequestResult requestResult = openLoanService.queryAllUnclearLoanerInfoPage(param);
            if (requestResult.getCode() == StatusCode.CODE_OK && !requestResult.getData().equals("[]")) {
                try {
                    String data = requestResult.getData();
                    List<LawLoanerBasicInfo> lawLoanerBasicInfoList = JSONObject.parseArray(data, LawLoanerBasicInfo.class);
                    handleUnclearLawInfo(lawLoanerBasicInfoList);
                    page++;
                } catch (Exception e) {
                    log.error(e + "");
                    count++;
                }
            } else if (requestResult.getCode() == StatusCode.CODE_OK && requestResult.getData().equals("[]")) {
                break;
            } else {
                log.error("调用loan_op_asset查询未结清人员信息错误, " + requestResult.getCode() + ", " + requestResult.getMessage());
                count++;
            }
        }
        if (count >= COUNT_LIMIT) {
            flag = false;
        }
        log.info("LawInfoQueryServiceImpl.verifyAllUnclearLawInfo 执行结束，flag:" + flag + ", 总次数count:" + count);
        return flag;
    }

    @Override
    public LawApiQueryResponse queryLoanerCase(LawApiQueryRequest lawApiQueryRequest) {
        LawApiQueryResponse lawApiQueryResponse = new LawApiQueryResponse();
        try {
            checkRequestParam(lawApiQueryRequest);
            CasesLoanerInfo casesLoanerInfo = fdUserMapper.queryOne(lawApiQueryRequest);
            if (null != casesLoanerInfo && DateUtil.isLatestDate(casesLoanerInfo.getUpdatedAt(), DAYS_LIMIT)) {
                if (!lawApiQueryRequest.getUserId().equals(casesLoanerInfo.getUserId())) {
                    log.error("请求的userId和库中的不一致");
                }
                // 拼装参数返回 casesLoanerInfo
            } else {
                // 查询中互金系统
                // sendRequest();
            }

        } catch (AppException e) {

        } catch (Exception ex) {

        }
        return null;
    }

    private void checkRequestParam(LawApiQueryRequest lawApiQueryRequest) {
        if (lawApiQueryRequest == null) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN);
        }
        if (StringUtils.isEmpty(lawApiQueryRequest.getBusinessId()) && StringUtils.length(lawApiQueryRequest.getBusinessId()) != 32) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":businessId错误");
        }
        if (null == lawApiQueryRequest.getUserId()) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":userId不能为空");
        }
        if (StringUtils.isEmpty(lawApiQueryRequest.getName())) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":name不能为空");
        }
        if (null == lawApiQueryRequest.getUserType()) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":userType不能为空");
        }
        if (null == lawApiQueryRequest.getCardType()) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":cardType不能为空");
        }
        if (StringUtils.isEmpty(lawApiQueryRequest.getCardId())) {
            throw new AppException(LawApiResponceCodeConst.CODE_9998, LawApiResponceCodeConst.CODE_9998_CN + ":cardId不能为空");
        }
    }

    private void handleUnclearLawInfo(List<LawLoanerBasicInfo> lawLoanerBasicInfoList) {
        log.info("LawInfoQueryServiceImpl.handleUnclearLawInfo，开始，请求数量size:" + lawLoanerBasicInfoList.size());
        // 存在的caseInfo
        List<LawLoanerBasicInfo> existLoaners = fdUserMapper.batchQueryLoaner(lawLoanerBasicInfoList);
        // 过期的caseInfo
        List<LawLoanerBasicInfo> overdueLoanerList = existLoaners.stream().filter(loaner -> !DateUtil.isLatestDate(loaner.getLastRequestDate(), DAYS_LIMIT)).collect(Collectors.toList());
        // 插入的cases
        removeLoaners(lawLoanerBasicInfoList, existLoaners);
        List<CasesLoanerInfo> insertCases = new ArrayList<>();
        for (LawLoanerBasicInfo loanerBasicInfo : lawLoanerBasicInfoList) {
            insertCases.add(sendRequest(loanerBasicInfo));
        }
        // 更新的cases
        List<CasesLoanerInfo> updateCases = new ArrayList<>();
        for (LawLoanerBasicInfo loanerBasicInfo : overdueLoanerList) {
            updateCases.add(sendRequest(loanerBasicInfo));
        }

        log.info("LawInfoQueryServiceImpl.handleUnclearLawInfo，请求中互金成功，准备入库");
        // 入库
        if (CollectionUtils.isNotEmpty(insertCases)) {
            fdUserMapper.batchInsert(insertCases);
        }
        if (CollectionUtils.isNotEmpty(updateCases)) {
            fdUserMapper.batchUpdate(updateCases);
        }
        log.info("LawInfoQueryServiceImpl.handleUnclearLawInfo，结束，入库成功");
    }

    private CasesLoanerInfo sendRequest(LawLoanerBasicInfo loanerInfo) {
        CasesRequestRecord casesRequestRecord = new CasesRequestRecord();
        casesRequestRecord.setUserId(loanerInfo.getUserId());
        casesRequestRecord.setStatus(LawReqRecodeConst.WAITING);

        CasesLoanerInfo casesLoanerInfo = new CasesLoanerInfo();

        String reponseStr = "";

        Integer userType = loanerInfo.getUserType();
        try {
            if (LawUserTypeConst.NATURAL.equals(userType)) {
                NaturalQueryRequest naturalQueryRequest = new NaturalQueryRequest(loanerInfo.getName(), loanerInfo.getCardId());

                casesRequestRecord.setRequestData(JSON.toJSONString(naturalQueryRequest));
                casesRequestRecord.setRequestType(LawUserTypeConst.NATURAL);
                casesRequestRecord.setBusinessId(naturalQueryRequest.getTransactionNo());
                casesRequestRecordMapper.insert(casesRequestRecord);

                // reponseStr = LawQueryNifaHelper.sendPost(LawRequestConst.NATRUAL_PATH, naturalQueryRequest, casesRequestRecord);
                reponseStr = "{\"code\":\"0\",\"data\":\"{\\\"count\\\":{\\\"civil_count_total\\\":85,\\\"is_sxbzxr\\\":0,\\\"larq_stat\\\":\\\"2002(1),2003(3),2004(1),2007(1),2008(2),2009(2),2010(1),2012(3),2013(1),2014(7),2015(5),2016(10),2017(18),2018(22),2019(24),其他(1)\\\",\\\"money_other\\\":8,\\\"count_yuangao\\\":22,\\\"money_jie_beigao\\\":11,\\\"administrative_count_total\\\":5,\\\"count_other\\\":14,\\\"criminal_count_total\\\":0,\\\"has_cases\\\":1,\\\"count_beigao\\\":66,\\\"count_jie_beigao\\\":57,\\\"money_yuangao\\\":12,\\\"money_beigao\\\":12}}\",\"message\":\"成功\",\"requestId\":\"e0237c53605a4449abe6df89b62b913f\",\"result\":\"0\"}";
            } else if (LawUserTypeConst.COMPANY.equals(userType)) {
                CompanyQueryRequest companyQueryRequest = new CompanyQueryRequest(loanerInfo.getName());

                casesRequestRecord.setRequestData(JSON.toJSONString(companyQueryRequest));
                casesRequestRecord.setRequestType(LawUserTypeConst.COMPANY);
                casesRequestRecord.setBusinessId(companyQueryRequest.getTransactionNo());
                casesRequestRecordMapper.insert(casesRequestRecord);

                // reponseStr = LawQueryNifaHelper.sendPost(LawRequestConst.ORG_PATH, companyQueryRequest,casesRequestRecord);
                reponseStr = "{\"code\":\"0\",\"data\":\"{\\\"count\\\":{\\\"civil_count_total\\\":14,\\\"is_sxbzxr\\\":1,\\\"larq_stat\\\":\\\"2015(6),2016(10),2017(6),2018(3),2019(3)\\\",\\\"money_other\\\":10,\\\"count_yuangao\\\":0,\\\"money_jie_beigao\\\":12,\\\"administrative_count_total\\\":0,\\\"count_other\\\":1,\\\"criminal_count_total\\\":0,\\\"has_cases\\\":1,\\\"count_beigao\\\":27,\\\"count_jie_beigao\\\":25,\\\"money_yuangao\\\":0,\\\"money_beigao\\\":12}}\",\"message\":\"成功\",\"requestId\":\"e4c61406540e41158bc483f9b34225ae\",\"result\":\"0\"}";
            }


            casesRequestRecord.setResponseData(reponseStr);
            casesRequestRecord.setUpdatedAt(new Date());

            if (StringUtils.isNotBlank(reponseStr)) {
                LawResultResponse lawResultResponse = JSONObject.parseObject(reponseStr, LawResultResponse.class);
                casesRequestRecord.setStatus(LawReqRecodeConst.SUCCESS);
                casesRequestRecord.setResponseCode(lawResultResponse.getCode());
                casesRequestRecord.setResponseCodeMsg(lawResultResponse.getMessage());

                casesLoanerInfo.setUserId(loanerInfo.getUserId());
                casesLoanerInfo.setName(loanerInfo.getName());
                casesLoanerInfo.setUserType(loanerInfo.getUserType());
                casesLoanerInfo.setCardType(loanerInfo.getCardType());
                casesLoanerInfo.setCardId(loanerInfo.getCardId());
                casesLoanerInfo.setUpdatedAt(new Date());
                if (ZERO.equals(lawResultResponse.getCode()) && ZERO.equals(lawResultResponse.getResult())) {
                    Map dataMap = (Map) (JSON.parse(lawResultResponse.getData()));
                    LawDetailResponse lawDetail = JSONObject.parseObject(MapUtils.getString(dataMap, LawResponseConst.COUNT), LawDetailResponse.class);
                    BeanUtils.copyProperties(casesLoanerInfo, lawDetail);
                } else if (ZERO.equals(lawResultResponse.getCode()) && ONE.equals(lawResultResponse.getResult())) {
                    // 未查询到,正常处理
                } else {
                    // 异常查询5次
                    log.error(lawResultResponse.getCode() + "," + lawResultResponse.getMessage());
                    throw new AppException(LawApiResponceCodeConst.CODE_9997, lawResultResponse.getCode() + ":" + lawResultResponse.getMessage());
                }
                casesRequestRecordMapper.update(casesRequestRecord);
                return casesLoanerInfo;
            }
        } catch (AppException e) {
            casesRequestRecord.setStatus(LawReqRecodeConst.FAIL);
            casesRequestRecord.setResponseCodeMsg(e.getMessage());
            casesRequestRecordMapper.update(casesRequestRecord);
            throw new AppException(LawApiResponceCodeConst.CODE_9997, StringUtils.isBlank(e.getMessage()) ? LawApiResponceCodeConst.CODE_9997_CN : e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new AppException(LawApiResponceCodeConst.CODE_9999, LawApiResponceCodeConst.CODE_9999_CN);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage());
            throw new AppException(LawApiResponceCodeConst.CODE_9999, LawApiResponceCodeConst.CODE_9999_CN);
        }
        return casesLoanerInfo;
    }

    private void removeLoaners(List<LawLoanerBasicInfo> largeList, List<LawLoanerBasicInfo> smallList) {
        Iterator<LawLoanerBasicInfo> iterator = largeList.iterator();
        while (iterator.hasNext()) {
            LawLoanerBasicInfo large = iterator.next();
            for (LawLoanerBasicInfo small : smallList) {
                if (large.getUserId().equals(small.getUserId())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
