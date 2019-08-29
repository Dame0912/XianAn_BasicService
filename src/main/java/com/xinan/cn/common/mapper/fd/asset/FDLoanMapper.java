package com.xinan.cn.common.mapper.fd.asset;

import com.xinan.cn.common.bean.entities.fd.asset.FDLoan;

public interface FDLoanMapper {
    /**
     * 根据请求Id获取资产信息
     */
    FDLoan getByApplyId(String applyId);
}
