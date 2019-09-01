package com.xinan.cn.common.mapper.plan;

import com.xinan.cn.common.bean.entities.plan.PlanConfig;

import java.util.Map;

public interface PlanConfigMapper {
    /**
     * 根据planId查询计划配置
     */
    PlanConfig getPlanConfigByPlanId(Long planId);

    /**
     * 根据planId更新计划配置
     */
    Integer updateByPlanId(PlanConfig planConfig);

    /**
     * 批量更新 last_check_time
     */
    int batchUpdateLastCheckTime(Map<String,Object> param);
}
