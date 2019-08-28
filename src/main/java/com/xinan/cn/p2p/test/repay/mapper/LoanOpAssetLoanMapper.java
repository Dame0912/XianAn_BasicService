package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.LoanOpAssetLoan;

public interface LoanOpAssetLoanMapper {
    /**
     * 根据请求Id获取资产信息
     */
    LoanOpAssetLoan getByApplyId(String applyId);
}
