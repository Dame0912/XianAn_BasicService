package com.xinan.cn.common.mapper.p2p.trading;

import com.xinan.cn.common.bean.entities.p2p.trading.P2PPaybackPlan;

import java.util.List;

public interface P2PPaybackPlanMapper {
    List<P2PPaybackPlan> findListBySkuId(Long skuId);
}
