package com.xinan.cn.common.service.plan.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.AllPlanInfo;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.service.plan.intf.PeriodPlanManageService;
import com.xinan.cn.common.service.plan.intf.PeriodPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<PeriodPlanVO> getPeriodPlanVO(LoanSimpleInfoVO loanSimpleInfoVO) {
        final AllPlanInfo allPlanInfo = periodPlanService.getAllPlan(null, loanSimpleInfoVO);
        List<PeriodPlan> fdPeriodPlanList = allPlanInfo.getFdPeriodPlan();
        Long p2pLastCheckTime = allPlanInfo.getP2pPlanConfig().getLastCheckTime();
        Long fdLastCheckTime = allPlanInfo.getFdPlanConfig().getLastCheckTime();
        List<PeriodPlanVO> periodPlanVOList = new ArrayList<>();
        allPlanInfo.getP2pPeriodPlan().forEach(periodPlan -> {
            Integer period = periodPlan.getPeriod();
            if (0 == period) {
                return;
            }
            PeriodPlan fdPlan = fdPeriodPlanList.stream().filter(fdPeriodPlan -> fdPeriodPlan.getPeriod() == period).findFirst().get();
            PeriodPlanVO periodPlanVO = new PeriodPlanVO();
            periodPlanVO.setPeriod(periodPlan.getPeriod());
            periodPlanVO.setPlanRepayDate(String.valueOf(periodPlan.getPlanRepayDate()));
            periodPlanVO.setP2pPlanId(String.valueOf(allPlanInfo.getP2pPlanId()));
            periodPlanVO.setP2pPlanStatus(periodPlan.getPlanStatus());
            periodPlanVO.setP2pIsOverdue(periodPlan.getIsOverdue());
            periodPlanVO.setP2pClaimStatus(periodPlan.getClaimStatus());
            periodPlanVO.setP2pLastCheckTime(String.valueOf(p2pLastCheckTime));
            periodPlanVO.setFdPlanId(String.valueOf(allPlanInfo.getFdPlanId()));
            periodPlanVO.setFdPlanStatus(fdPlan.getPlanStatus());
            periodPlanVO.setFdIsOverdue(fdPlan.getIsOverdue());
            periodPlanVO.setFdClaimStatus(fdPlan.getClaimStatus());
            periodPlanVO.setFdLastCheckTime(String.valueOf(fdLastCheckTime));
            periodPlanVOList.add(periodPlanVO);
        });
        return periodPlanVOList;
    }
}
