package com.xinan.cn.common.mapper.plan;

import com.xinan.cn.common.bean.entities.plan.PlanConfig;

public interface PlanConfigMapper {
    /**
     * 根据planId查询计划配置
     */
    PlanConfig getPlanConfigByPlanId(Long planId);

    /**
     * 根据planId更新计划配置
     */
    Integer updateByPlanId(PlanConfig planConfig);
}
