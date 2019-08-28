package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.CorePlanConfig;

public interface CorePlanConfigMapper {
    /**
     * 根据planId查询计划配置
     */
    CorePlanConfig getPlanConfigByPlanId(Long planId);

    /**
     * 根据planId更新计划配置
     */
    Integer updateByPlanId(CorePlanConfig planConfig);
}
