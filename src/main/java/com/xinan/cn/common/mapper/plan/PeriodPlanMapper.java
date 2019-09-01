package com.xinan.cn.common.mapper.plan;

import com.xinan.cn.common.bean.entities.plan.PeriodPlan;

import java.util.List;

public interface PeriodPlanMapper {

    /**
     * 更新某一期的计划
     */
    Integer updateOneByPlanId(PeriodPlan periodPlan);

    /**
     * 获取某一期计划
     */
    PeriodPlan getOnePeriodPlan(PeriodPlan periodPlan);

    /**
     * 获取全部计划
     */
    List<PeriodPlan> listPlan(Long planId);

    List<Integer> queryOverdueAndNoRepayWithPeriod(PeriodPlan periodPlan);
}
