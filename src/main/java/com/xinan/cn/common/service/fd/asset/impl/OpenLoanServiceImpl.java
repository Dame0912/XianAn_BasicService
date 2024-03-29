package com.xinan.cn.common.service.fd.asset.impl;

import com.xinan.cn.common.bean.RequestResult;
import com.xinan.cn.common.bean.WeiyanException;
import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.service.fd.asset.intf.OpenLoanService;
import com.xinan.cn.p2p.litagation.bean.dto.LawLoanerBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class OpenLoanServiceImpl implements OpenLoanService {

    @Autowired
    private FDLoanMapper fdLoanMapper;

    @Override
    public RequestResult queryAllUnclearLoanerInfoPage(Map<String, Object> param) {
        try {
            List<LawLoanerBasicInfo> lawLoaners = fdLoanMapper.getAllUnclearLoanerInfoPage(param);
            return RequestResult.success(lawLoaners);
        } catch (Exception e) {
            log.error("查询所有未结清人员信息异常,{}", e);
            return RequestResult.error(WeiyanException.getDefault(), e.toString());
        }
    }
}
