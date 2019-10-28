package com.xinan.cn.p2p.report.nifa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.xinan.cn.p2p.report.nifa.bean.*;
import com.xinan.cn.p2p.report.nifa.bean.dto.*;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjInfo;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjRequestData;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjRequestRecord;
import com.xinan.cn.p2p.report.nifa.constant.*;
import com.xinan.cn.p2p.report.nifa.mapper.NifaPrjInfoMapper;
import com.xinan.cn.p2p.report.nifa.mapper.NifaPrjRequestDataMapper;
import com.xinan.cn.p2p.report.nifa.mapper.NifaPrjRequestRecordMapper;
import com.xinan.cn.p2p.report.nifa.service.intf.NifaReportQueryService;
import com.xinan.cn.common.service.BaseServiceAbstract;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NifaReportQueryServiceImpl extends BaseServiceAbstract implements NifaReportQueryService, NifaConstant {

    @Autowired
    private NifaPrjInfoMapper prjInfoMapper;
    @Autowired
    private NifaPrjRequestRecordMapper requestRecordMapper;
    @Autowired
    private NifaPrjRequestDataMapper requestDataMapper;

    @Override
    public Map<String, Object> queryPrjInfoPage(NifaReportQueryParamVO requestParam) {
        // 查询总数
        Long count = prjInfoMapper.queryCount(requestParam);
        // 查询数据
        List<NifaPrjInfo> nifaPrjInfos = prjInfoMapper.queryShowPage(requestParam);
        List<NifaPrjRepInfoDTO> result = handlePrjInfoResult(nifaPrjInfos);
        return pageParamJoin(count, result);
    }

    @Override
    public List<NifaReqRecordDataDTO> queryReqRecordPage(Map<String, String > param) {
        // 查询请求记录
        List<NifaPrjRequestRecord> nifaReqRecordInfos = requestRecordMapper.queryShowPage(param);
        List<Long> requestIds = nifaReqRecordInfos.stream().map(NifaPrjRequestRecord::getId).collect(Collectors.toList());
        if(null == requestIds || requestIds.size() == 0){
            return null;
        }
        // 查询请求数据
        List<NifaPrjRequestData> nifaPrjRequestDatas = requestDataMapper.queryReqDataList(requestIds);
        Long prjNum = null;
        if(StringUtils.isNotBlank(param.get("prjNum"))){
            prjNum = Long.parseLong(param.get("prjNum"));
        }
        List<NifaReqRecordDataDTO> result = handleReqRecordResult(nifaReqRecordInfos, nifaPrjRequestDatas, prjNum);
        return result;
    }

    private List<NifaReqRecordDataDTO> handleReqRecordResult(List<NifaPrjRequestRecord> nifaReqRecordInfos, List<NifaPrjRequestData> nifaPrjRequestDatas, Long prjNum) {
        List<NifaReqRecordDataDTO> reqRecordDataList = new ArrayList<>();
        nifaReqRecordInfos.forEach(nifaPrjRequestRecord -> {
            NifaReqRecordDataDTO reqRecordData = new NifaReqRecordDataDTO();

            Optional<NifaPrjRequestData> nifaPrjRequestDataOptional = nifaPrjRequestDatas.stream().filter(nifaPrjRequestData -> nifaPrjRequestData.getRequestId() == nifaPrjRequestRecord.getId()).findFirst();
            if (nifaPrjRequestDataOptional.isPresent()) {
                NifaPrjRequestData nifaPrjRequestData = nifaPrjRequestDataOptional.get();
                List<Long> prjNumList = getPrjNumList(nifaPrjRequestData.getRequestData());
                if(null != prjNum && !prjNumList.contains(prjNum)){
                    return;
                }
                reqRecordData.setPrjNumList(null == prjNumList ? "" : StringUtils.join(prjNumList, "|"));
                reqRecordData.setRequestData(nifaPrjRequestData.getRequestData());
                reqRecordData.setResponseData(nifaPrjRequestData.getResponseData());
            }
            reqRecordData.setId(String.valueOf(nifaPrjRequestRecord.getId()));
            reqRecordData.setTotalNum(nifaPrjRequestRecord.getTotalNum());
            reqRecordData.setType(nifaPrjRequestRecord.getType());
            reqRecordData.setStatus(nifaPrjRequestRecord.getStatus());
            reqRecordData.setErrorMsg(nifaPrjRequestRecord.getErrorMsg());
            reqRecordData.setCreatedAt(nifaPrjRequestRecord.getCreatedAt());
            reqRecordData.setUpdatedAt(nifaPrjRequestRecord.getUpdatedAt());
            reqRecordDataList.add(reqRecordData);
        });
        return reqRecordDataList;
    }

    private List<Long> getPrjNumList(String requestData) {
        if (StringUtils.isBlank(requestData)) {
            return null;
        }
        List<Long> resultList = new ArrayList<>();
        Map sdata = (Map) JSON.parse(String.valueOf(((Map) JSON.parse(requestData)).get("sdata")));
        if (null != sdata.get(PRJ_INFO_KEY)) {
            JSONArray repayprojects = JSONArray.parseArray(String.valueOf(sdata.get(PRJ_INFO_KEY)));
            repayprojects.forEach(repayproject -> {
                String repayproject1 = (String) repayproject;
                Long prjNum = Long.parseLong(repayproject1.split("\\|")[1]);
                resultList.add(prjNum);
            });
        } else if (null != sdata.get(PRJ_CHANGE_KEY)) {
            JSONArray repayprojects = JSONArray.parseArray(String.valueOf(sdata.get(PRJ_CHANGE_KEY)));
            repayprojects.forEach(repayproject -> {
                String repayproject1 = (String) repayproject;
                Long prjNum = Long.parseLong(repayproject1.split("\\|")[0]);
                resultList.add(prjNum);
            });
        }
        return resultList;
    }


    /**
     * 查询出的项目信息数据处理
     *
     * @param nifaPrjInfos
     */
    private List<NifaPrjRepInfoDTO> handlePrjInfoResult(List<NifaPrjInfo> nifaPrjInfos) {
        List<NifaPrjRepInfoDTO> retList = new ArrayList<>();
        nifaPrjInfos.forEach(prjInfo -> {
            Map<String, Object> dataMap = new HashMap<>();
            String prjType = prjInfo.getType();
            Map<String, Object> originalData = JSON.parseObject(prjInfo.getData());
            switch (prjType) {
                case PRJ_NATURAL: // 项目信息 + 借款人(个人)
                    ProjectInfo naturalPrjInfo = transfer(String.valueOf(originalData.get(PRJ_INFO_KEY)), ProjectInfo.class);
                    NaturalInfo naturalInfo = transfer(String.valueOf(originalData.get(PRJ_NATURAL_KEY)), NaturalInfo.class);
                    dataMap.put("repType", PRJ_NATURAL);
                    dataMap.put("repInfo", new PrjNaturalDTO(naturalPrjInfo, naturalInfo));
                    break;
                case PRJ_LEGAL: // 项目信息 + 借款人(企业)
                    ProjectInfo legalPrjInfo = transfer(String.valueOf(originalData.get(PRJ_INFO_KEY)), ProjectInfo.class);
                    LegalInfo legalInfo = transfer(String.valueOf(originalData.get(PRJ_LEGAL_KEY)), LegalInfo.class);
                    dataMap.put("repType", PRJ_LEGAL);
                    dataMap.put("repInfo", new PrjLegalDTO(legalPrjInfo, legalInfo));
                    break;
                case PRJ_CHANGE: // 项目变更
                    ProjectChangeInfo prjChangeInfo = transfer(String.valueOf(originalData.get(PRJ_CHANGE_KEY)), ProjectChangeInfo.class);
                    dataMap.put("repType", PRJ_CHANGE);
                    dataMap.put("repInfo", new PrjChangeDTO(prjChangeInfo));
                    break;
            }
            NifaPrjRepInfoDTO nifaPrjRepInfoDTO = new NifaPrjRepInfoDTO();
            nifaPrjRepInfoDTO.setPrjNum(prjInfo.getPrjNum());
            nifaPrjRepInfoDTO.setPrjStatus(NifaPrjInfoStatusEnum.getDesc(prjInfo.getPrjStatus()));
            nifaPrjRepInfoDTO.setType(NifaPrjInfoTypeEnum.getDesc(prjInfo.getType()));
            nifaPrjRepInfoDTO.setReportData(dataMap);
            nifaPrjRepInfoDTO.setStatus(NifaReportStatusEnum.getDesc(prjInfo.getStatus()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nifaPrjRepInfoDTO.setErrorMsg(prjInfo.getErrorMsg());
            nifaPrjRepInfoDTO.setCreatedAt(dateFormat.format(prjInfo.getCreatedAt()));
            nifaPrjRepInfoDTO.setUpdatedAt(dateFormat.format(prjInfo.getUpdatedAt()));
            nifaPrjRepInfoDTO.setId(String.valueOf(prjInfo.getId()));
            retList.add(nifaPrjRepInfoDTO);
        });

        return retList;
    }

    private <T> T transfer(String param, Class<T> clazz) {
        try {
            List<String> valueList = Arrays.asList(param.split("\\|", -1));
            T instance = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                SerialNum serialNum = field.getAnnotation(SerialNum.class);
                String fieldVal = valueList.get(serialNum.ordinal() - 1);
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method m = clazz.getMethod("set" + name, String.class);
                m.invoke(instance, fieldVal);
            }
            return instance;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("NifaReportQueryServiceImpl.transfer,数据转换异常");
        }
    }

}
