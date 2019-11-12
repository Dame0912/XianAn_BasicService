package com.xinan.cn.common.mapper.fd.asset;

import com.xinan.cn.common.bean.PageParam;
import com.xinan.cn.common.bean.entities.fd.asset.FDLoan;
import com.xinan.cn.p2p.litagation.bean.LawLoanerInfo;

import java.util.List;
import java.util.Map;

public interface FDLoanMapper {
    /**
     * 根据请求Id获取资产信息
     */
    FDLoan getByApplyId(String applyId);

    List<FDLoan> queryUnclearLoanPage(PageParam<Map<String, Object>> queryUnclearLoanParam);

    List<LawLoanerInfo> getAllUnclearLoanerInfo();
}
