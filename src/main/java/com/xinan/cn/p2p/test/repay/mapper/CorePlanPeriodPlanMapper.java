package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.CorePlanPeriodPlan;

import java.util.List;

public interface CorePlanPeriodPlanMapper {

    /**
     * 更新某一期的计划
     */
    Integer updateOneByPlanId(CorePlanPeriodPlan periodPlan);

    /**
     * 获取某一期计划
     */
    CorePlanPeriodPlan getOnePeriodPlan(CorePlanPeriodPlan periodPlan);

    /**
     * 获取全部计划
     */
    List<CorePlanPeriodPlan> listPlan(Long planId);
}
