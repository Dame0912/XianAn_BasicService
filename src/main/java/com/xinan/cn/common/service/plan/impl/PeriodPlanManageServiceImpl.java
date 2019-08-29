package com.xinan.cn.common.service.plan.impl;

import com.xinan.cn.common.bean.dto.plan.AllPeriodsPlan;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.service.plan.intf.PeriodPlanManageService;
import com.xinan.cn.common.service.plan.intf.PeriodPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Author lyq
 * @Date 2019/8/29 22:57
 **/
@Slf4j
@Service
public class PeriodPlanManageServiceImpl implements PeriodPlanManageService {
    @Autowired
    private PeriodPlanService periodPlanService;

    @Override
    public boolean checkIsAllowRepay(List<PeriodPlan> periodPlans, Integer period) {
        Optional<PeriodPlan> firstPeriodPlan = periodPlans.stream().filter(periodPlan -> periodPlan.getPeriod() == 1 && periodPlan.getPlanStatus() == 30).findFirst();
        if (!firstPeriodPlan.isPresent()) {
            log.error("第一期必须还款成功!");
            return false;
        }
        Optional<PeriodPlan> processLoanPeriodPlan = periodPlans.stream().filter(periodPlan -> periodPlan.getPeriod() <= period && periodPlan.getPeriod() > 1 && periodPlan.getPlanStatus() != 0).findAny();
        if (processLoanPeriodPlan.isPresent()) {
            log.error("有不是未处理的计划!");
            return false;
        }
        return true;
    }

    @Override
    public PeriodPlanVO getPeriodPlanVO(Long skuId) {
        AllPeriodsPlan allPeriodsPlan = periodPlanService.getAllPlanBySkuId(skuId);



        return null;
    }
}
