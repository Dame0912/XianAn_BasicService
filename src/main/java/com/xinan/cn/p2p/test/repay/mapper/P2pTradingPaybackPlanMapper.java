package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.P2pTradingPaybackPlan;

import java.util.List;

public interface P2pTradingPaybackPlanMapper {
    List<P2pTradingPaybackPlan> findListBySkuId(Long skuId);
}
