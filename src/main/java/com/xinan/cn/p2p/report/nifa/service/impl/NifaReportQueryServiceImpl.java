package com.xinan.cn.p2p.report.nifa.service.impl;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.p2p.report.nifa.bean.*;
import com.xinan.cn.p2p.report.nifa.bean.dto.NifaPrjRepInfoDTO;
import com.xinan.cn.p2p.report.nifa.bean.dto.PrjChangeDTO;
import com.xinan.cn.p2p.report.nifa.bean.dto.PrjLegalDTO;
import com.xinan.cn.p2p.report.nifa.bean.dto.PrjNaturalDTO;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjInfo;
import com.xinan.cn.p2p.report.nifa.constant.*;
import com.xinan.cn.p2p.report.nifa.mapper.NifaPrjInfoMapper;
import com.xinan.cn.p2p.report.nifa.service.intf.NifaReportQueryService;
import com.xinan.cn.service.BaseServiceAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class NifaReportQueryServiceImpl extends BaseServiceAbstract implements NifaReportQueryService, NifaConstant {

    @Autowired
    private NifaPrjInfoMapper prjInfoMapper;

    @Override
    public Map<String, Object> queryReportInfoPage(NifaReportQueryParamVO requestParam) {
        // 查询总数
        Long count = prjInfoMapper.queryCount(requestParam);
        // 查询数据
        List<NifaPrjInfo> nifaPrjInfos = prjInfoMapper.queryShowPage(requestParam);
        List<NifaPrjRepInfoDTO> result = handleResult(nifaPrjInfos);
        return pageParamJoin(count, result);
    }

    /**
     * 查询出的数据处理
     *
     * @param nifaPrjInfos
     */
    private List<NifaPrjRepInfoDTO> handleResult(List<NifaPrjInfo> nifaPrjInfos) {
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
