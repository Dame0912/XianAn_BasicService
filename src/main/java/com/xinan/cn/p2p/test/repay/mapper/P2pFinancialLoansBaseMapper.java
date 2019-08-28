package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.P2pFinancialLoansBase;
import tk.mybatis.mapper.common.Mapper;

public interface P2pFinancialLoansBaseMapper extends Mapper<P2pFinancialLoansBase> {

    P2pFinancialLoansBase getBySkuId(Long skuId);
}
