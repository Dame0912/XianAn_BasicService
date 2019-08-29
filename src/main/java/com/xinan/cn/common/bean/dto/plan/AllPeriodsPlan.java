package com.xinan.cn.common.bean.dto.plan;

import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author lyq
 * @Date 2019/8/29 22:46
 **/
@Data
public class AllPeriodsPlan {
    private Long p2pPlanId;//理财
    private List<PeriodPlan> p2pPeriodPlan;
    private Map<Long, List<PeriodPlan>> p2pInvestPeriodPlans;//理财投资
    private Long fdPlanId;//房贷
    private List<PeriodPlan> fdPeriodPlan;
    private Long fdInvestPlanId;//房贷投资
    private List<PeriodPlan> fdInvestPeriodPlan;
}
