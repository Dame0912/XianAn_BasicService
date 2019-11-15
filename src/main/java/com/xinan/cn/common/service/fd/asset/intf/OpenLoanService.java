package com.xinan.cn.common.service.fd.asset.intf;

import com.xinan.cn.common.bean.RequestResult;

import java.util.Map;

public interface OpenLoanService {

    /**
     * 查询所有未结清人员的基本信息
     */
    RequestResult queryAllUnclearLoanerInfoPage(Map<String, Object> param);
}
