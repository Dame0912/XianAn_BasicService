package com.xinan.cn.common.service.plan.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.AllPeriodsPlan;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.service.plan.intf.PeriodPlanManageService;
import com.xinan.cn.common.service.plan.intf.PeriodPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 计划管理类
 *
 * @Author lyq
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
    public List<PeriodPlanVO> getPeriodPlanVO(LoanSimpleInfoVO loanSimpleInfoVO) {
        final AllPeriodsPlan allPeriodsPlan = periodPlanService.getAllPlan(null, loanSimpleInfoVO);
        List<PeriodPlan> fdPeriodPlanList = allPeriodsPlan.getFdPeriodPlan();
        List<PeriodPlanVO> periodPlanVOList = new ArrayList<>();
        allPeriodsPlan.getP2pPeriodPlan().forEach(periodPlan -> {
            Integer period = periodPlan.getPeriod();
            if(0 == period){
                return;
            }
            PeriodPlan fdPlan = fdPeriodPlanList.stream().filter(fdPeriodPlan -> fdPeriodPlan.getPeriod() == period).findFirst().get();
            PeriodPlanVO p2pPeriodPlanVO = new PeriodPlanVO();
            p2pPeriodPlanVO.setPeriod(periodPlan.getPeriod());
            p2pPeriodPlanVO.setPlanRepayDate(String.valueOf(periodPlan.getPlanRepayDate()));
            p2pPeriodPlanVO.setP2pPlanStatus(periodPlan.getPlanStatus());
            p2pPeriodPlanVO.setP2pIsOverdue(periodPlan.getIsOverdue());
            p2pPeriodPlanVO.setP2pClaimStatus(periodPlan.getClaimStatus());
            p2pPeriodPlanVO.setFdPlanStatus(fdPlan.getPlanStatus());
            p2pPeriodPlanVO.setFdIsOverdue(fdPlan.getIsOverdue());
            p2pPeriodPlanVO.setFdClaimStatus(fdPlan.getClaimStatus());
            periodPlanVOList.add(p2pPeriodPlanVO);
        });
        return periodPlanVOList;
    }
}
