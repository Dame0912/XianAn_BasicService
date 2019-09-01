package com.xinan.cn.common.service.plan.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.AllPlanInfo;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.bean.entities.plan.PlanConfig;
import com.xinan.cn.common.constants.SymbolsConst;
import com.xinan.cn.common.mapper.plan.PeriodPlanMapper;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
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
    @Autowired
    private PlanConfigMapper planConfigMapper;

    @Override
    public AllPlanInfo getAllPlan(Long skuId, LoanSimpleInfoVO... loanSimpleInfoVO) {
        LoanSimpleInfoVO loanSimpleInfo;
        if (null == loanSimpleInfoVO) {
            loanSimpleInfo = p2PLoanService.getLoanSimpleInfo(skuId);
        } else {
            loanSimpleInfo = loanSimpleInfoVO[0];
        }
        //P2P
        Long p2pPlanId = Long.parseLong(loanSimpleInfo.getP2pPlanId());
        List<PeriodPlan> p2pPeriodPlans = periodPlanMapper.listPlan(p2pPlanId);
        PlanConfig p2pPlanConfig = planConfigMapper.getPlanConfigByPlanId(p2pPlanId);
        //P2P投资
        String[] p2pInvestPlanIds = loanSimpleInfo.getP2pInvestPlanIds().split(SymbolsConst.VERTICAL_SEPARATOR);
        Map<Long, List<PeriodPlan>> p2pInvestPeriodPlans = new HashMap<>();
        Map<Long, PlanConfig> p2pInvestPlanConfigs = new HashMap<>();
        for (String p2pInvestPlanId : p2pInvestPlanIds) {
            Long investPlanId = Long.parseLong(p2pInvestPlanId);
            List<PeriodPlan> investPeriodPlans = periodPlanMapper.listPlan(investPlanId);
            PlanConfig p2pInvestPlanConfig = planConfigMapper.getPlanConfigByPlanId(investPlanId);
            p2pInvestPeriodPlans.put(investPlanId, investPeriodPlans);
            p2pInvestPlanConfigs.put(investPlanId, p2pInvestPlanConfig);
        }
        //FD
        Long fdPlanId = Long.parseLong(loanSimpleInfo.getFdPlanId());
        List<PeriodPlan> fdPeriodPlans = periodPlanMapper.listPlan(fdPlanId);
        PlanConfig fdPlanConfig = planConfigMapper.getPlanConfigByPlanId(fdPlanId);
        //FD投资
        Long fdInvestPlanId = Long.parseLong(loanSimpleInfo.getFdInvestPlanId());
        List<PeriodPlan> fdInvestPeriodPlans = periodPlanMapper.listPlan(fdInvestPlanId);
        PlanConfig fdInvestPlanConfig = planConfigMapper.getPlanConfigByPlanId(fdInvestPlanId);
        AllPlanInfo allPlanInfo = new AllPlanInfo();
        allPlanInfo.setP2pPlanId(p2pPlanId);
        allPlanInfo.setP2pPeriodPlan(p2pPeriodPlans);
        allPlanInfo.setP2pPlanConfig(p2pPlanConfig);
        allPlanInfo.setP2pInvestPeriodPlans(p2pInvestPeriodPlans);
        allPlanInfo.setP2pInvestPlanConfigs(p2pInvestPlanConfigs);
        allPlanInfo.setFdPlanId(fdPlanId);
        allPlanInfo.setFdPeriodPlan(fdPeriodPlans);
        allPlanInfo.setFdPlanConfig(fdPlanConfig);
        allPlanInfo.setFdInvestPlanId(fdInvestPlanId);
        allPlanInfo.setFdInvestPeriodPlan(fdInvestPeriodPlans);
        allPlanInfo.setFdInvestPlanConfig(fdInvestPlanConfig);
        return allPlanInfo;
    }


}
