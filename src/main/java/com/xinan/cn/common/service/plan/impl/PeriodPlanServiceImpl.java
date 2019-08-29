package com.xinan.cn.common.service.plan.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.AllPeriodsPlan;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.constants.SymbolsConst;
import com.xinan.cn.common.mapper.plan.PeriodPlanMapper;
import com.xinan.cn.common.service.p2p.asset.intf.P2PLoanService;
import com.xinan.cn.common.service.plan.intf.PeriodPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PeriodPlanServiceImpl implements PeriodPlanService {
    @Autowired
    private P2PLoanService p2PLoanService;
    @Autowired
    private PeriodPlanMapper periodPlanMapper;


    @Override
    public AllPeriodsPlan getAllPlanBySkuId(Long skuId) {
        LoanSimpleInfoVO loanSimpleInfo = p2PLoanService.getLoanSimpleInfo(skuId);
        //P2P
        Long p2pPlanId = Long.parseLong(loanSimpleInfo.getP2pPlanId());
        List<PeriodPlan> p2pPeriodPlans = periodPlanMapper.listPlan(p2pPlanId);
        //P2P投资
        String[] p2pInvestPlanIds = loanSimpleInfo.getP2pInvestPlanIds().split(SymbolsConst.JOIN_SYMBOL);
        Map<Long, List<PeriodPlan>> p2pInvestPeriodPlans = new HashMap<>();
        for (String p2pInvestPlanId : p2pInvestPlanIds) {
            Long investPlanId = Long.parseLong(p2pInvestPlanId);
            List<PeriodPlan> investPeriodPlans = periodPlanMapper.listPlan(investPlanId);
            p2pInvestPeriodPlans.put(investPlanId, investPeriodPlans);
        }
        //FD
        Long fdPlanId = Long.parseLong(loanSimpleInfo.getFdPlanId());
        List<PeriodPlan> fdPeriodPlans = periodPlanMapper.listPlan(fdPlanId);
        //FD投资
        Long fdInvestPlanId = Long.parseLong(loanSimpleInfo.getFdInvestPlanId());
        List<PeriodPlan> fdInvestPeriodPlans = periodPlanMapper.listPlan(fdInvestPlanId);
        AllPeriodsPlan allPeriodsPlan = new AllPeriodsPlan();
        allPeriodsPlan.setP2pPlanId(p2pPlanId);
        allPeriodsPlan.setP2pPeriodPlan(p2pPeriodPlans);
        allPeriodsPlan.setP2pInvestPeriodPlans(p2pInvestPeriodPlans);
        allPeriodsPlan.setFdPlanId(fdPlanId);
        allPeriodsPlan.setFdPeriodPlan(fdPeriodPlans);
        allPeriodsPlan.setFdInvestPlanId(fdInvestPlanId);
        allPeriodsPlan.setFdInvestPeriodPlan(fdInvestPeriodPlans);
        return allPeriodsPlan;
    }


}
