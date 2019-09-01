package com.xinan.cn.common.bean.dto.plan;

import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.bean.entities.plan.PlanConfig;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 所有计划信息
 *
 * @Author lyq
 **/
@Data
public class AllPlanInfo {
    private Long p2pPlanId;//理财
    private List<PeriodPlan> p2pPeriodPlan;
    private PlanConfig p2pPlanConfig;
    private Map<Long, List<PeriodPlan>> p2pInvestPeriodPlans;//理财投资
    private Map<Long, PlanConfig> p2pInvestPlanConfigs;
    private Long fdPlanId;//房贷
    private List<PeriodPlan> fdPeriodPlan;
    private PlanConfig fdPlanConfig;
    private Long fdInvestPlanId;//房贷投资
    private List<PeriodPlan> fdInvestPeriodPlan;
    private PlanConfig fdInvestPlanConfig;
}
