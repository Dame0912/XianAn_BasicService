package com.xinan.cn.p2p.test.repay.service.impl;

import com.xinan.cn.common.bean.entities.fd.asset.FDLoan;
import com.xinan.cn.common.bean.entities.fd.core.FDInvest;
import com.xinan.cn.common.bean.entities.fd.core.FDLoanSkuMapping;
import com.xinan.cn.common.bean.entities.p2p.asset.P2PLoan;
import com.xinan.cn.common.bean.entities.p2p.financial.P2PLoansBase;
import com.xinan.cn.common.bean.entities.p2p.trading.P2PPaybackPlan;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.mapper.fd.core.FDInvestMapper;
import com.xinan.cn.common.mapper.fd.core.FDLoanSkuMapper;
import com.xinan.cn.common.mapper.p2p.asset.P2PLoanMapper;
import com.xinan.cn.common.mapper.p2p.financial.P2PLoansBaseMapper;
import com.xinan.cn.common.mapper.p2p.trading.P2PPaybackPlanMapper;
import com.xinan.cn.common.mapper.plan.MoneyMapper;
import com.xinan.cn.common.mapper.plan.PeriodPlanMapper;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
import com.xinan.cn.p2p.test.repay.service.intf.AdvanceRepayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdvanceRepayServiceImpl implements AdvanceRepayService {

    @Autowired
    private P2PLoansBaseMapper p2pLoansBaseMapper;
    @Autowired
    private FDLoanMapper fdLoanMapper;
    @Autowired
    private FDLoanSkuMapper fdLoanSkuMapper;
    @Autowired
    private FDInvestMapper fdInvestMapper;
    @Autowired
    private P2PLoanMapper p2pLoanMapper;
    @Autowired
    private P2PPaybackPlanMapper p2pPaybackPlanMapper;
    @Autowired
    private PlanConfigMapper planConfigMapper;
    @Autowired
    private PeriodPlanMapper periodPlanMapper;
    @Autowired
    private MoneyMapper moneyMapper;


    @Override
    public void advanceRepay(Map<String, String> param) {
        Long skuId = Long.valueOf(param.get("skuId"));
        Integer period = Integer.parseInt(param.get("period"));
        Long p2pLoanPlanId;
        List<Long> p2pInvestPlanIds;
        Long phpLoanPlanId;
        Long phpInvestPlanId;

        //1、运营后台【xinan_uat_financial】
        //SELECT * FROM xinan_uat_financial.loans_base WHERE sku_id = 1165824340444418048;
        //loan_apply_no = 1164818839813963776	loan_user_id = 1151289975198814208
        //sku_id = 1151296121428721664	loan_id = 1165824340433518592
        P2PLoansBase loansBase = p2pLoansBaseMapper.getBySkuId(skuId);

        //2、房贷资产【loan_opuat_asset】
        //SELECT * FROM loan_opuat_asset.loan WHERE apply_id = 1164818839813963776;#loan_id=1165825253470584832 #plan_id=1165815365464330240
        FDLoan loanOpAssetLoan = fdLoanMapper.getByApplyId(String.valueOf(loansBase.getLoanApplyNo()));
        phpLoanPlanId = loanOpAssetLoan.getPlanId();

        //3、房贷资金【loan_opuat_core】
        //SELECT * FROM loan_opuat_core.loan_sku_mapping WHERE loan_id = 1165825253470584832; #sku_id = 1151309683861700608
        //SELECT * FROM loan_opuat_core.invest WHERE sku_id = 1165825254962073600; #invest_id=1151309683870089216 plan_id=1151309683966558208
        FDLoanSkuMapping loanOpCoreLoanSku = fdLoanSkuMapper.getByLoanId(loanOpAssetLoan.getLoanId());
        FDInvest loanOpCoreInvest = fdInvestMapper.getBySkuId(loanOpCoreLoanSku.getSkuId());
        phpInvestPlanId = loanOpCoreInvest.getPlanId();

        //4、P2P资产 asset
        //SELECT * FROM xinan_uat_asset.loan WHERE loan_id = 1165824340433518592; #plan_id = 1165815366271320064
        P2PLoan p2pAssetLoan = p2pLoanMapper.getByLoanId(loansBase.getLoanId());
        p2pLoanPlanId = p2pAssetLoan.getPlanId();

        //5、P2P资金 trading
        //SELECT * FROM xinan_uat_trading.payback_plan WHERE sku_id = 1151296121428721664;
        List<P2PPaybackPlan> p2pTradingPaybackPlanList = p2pPaybackPlanMapper.findListBySkuId(skuId);
        p2pInvestPlanIds = p2pTradingPaybackPlanList.stream().map(P2PPaybackPlan::getPlanId).collect(Collectors.toList());

        log.info("p2pLoanPlanId:" + p2pLoanPlanId + ", p2pInvestPlanIds:" + p2pInvestPlanIds + ", phpLoanPlanId:" + phpLoanPlanId + ", phpInvestPlanId:" + phpInvestPlanId);


        // 查询还款计划,第一期还款成功，中间没有处理中的
        List<PeriodPlan> p2pLoanPeriodPlans = periodPlanMapper.listPlan(p2pLoanPlanId);//P2P资产
        for (Long p2pInvestPlanId : p2pInvestPlanIds) {
            List<PeriodPlan> p2pInvestPeriodPlans = periodPlanMapper.listPlan(p2pInvestPlanId);//P2P资产投资人
        }
        List<PeriodPlan> phpLoanPeriodPlans = periodPlanMapper.listPlan(phpLoanPlanId);//房贷资产
        List<PeriodPlan> phpInvestPeriodPlans = periodPlanMapper.listPlan(phpInvestPlanId);//房贷资产投资人

        boolean checkPlan = checkPlan(Arrays.asList(p2pLoanPeriodPlans, phpLoanPeriodPlans), period);
        if (!checkPlan) {
            return;
        }

        //更改还款计划时间
        Optional<PeriodPlan> p2pPlanInPeriod = p2pLoanPeriodPlans.stream().filter(corePlanPeriodPlan -> corePlanPeriodPlan.getPeriod() == period).findFirst();
        Long planRepayDate = p2pPlanInPeriod.get().getPlanRepayDate();
        Long advanceRepayTime = planRepayDate - 86400000 * 2;

        // 将这些时间都更新
        //corePlanPeriodPlanMapper.updateOneByPlanId(advanceRepayTime,p2pLoanPlanId);
        //Long p2pLoanPlanId;
        //List<Long> p2pInvestPlanIds;
        //Long phpLoanPlanId;
        //Long phpInvestPlanId;

        //UPDATE xinan_uat_core_plan.plan_config SET last_check_time =1575504000000 WHERE plan_id = 1166150368899469312;#P2P资产
        //UPDATE xinan_uat_core_plan.plan_config SET last_check_time =1575504000000 WHERE plan_id = 1166150367886131200;#P2P资产投资人
        //UPDATE xinan_uat_core_plan.plan_config SET last_check_time =1575504000000 WHERE plan_id = 1166151157227294720;#房贷资产
        //UPDATE xinan_uat_core_plan.plan_config SET last_check_time =1575504000000 WHERE plan_id = 1166150369463189504;#房贷资产投资人


        //前面需要还款成功

        //查询p2p的planId
        //查询p2p投资人的planId
        //查询房贷的planId
        //查询房贷投资人的planId
    }

    private boolean checkPlan(List<List<PeriodPlan>> LoanPeriodPlanLists, Integer period) {
        for (List<PeriodPlan> p2pLoanPeriodPlans : LoanPeriodPlanLists) {
            Optional<PeriodPlan> firstP2pLoanPeriodPlan = p2pLoanPeriodPlans.stream().filter(corePlanPeriodPlan -> corePlanPeriodPlan.getPeriod() == 1 && corePlanPeriodPlan.getPlanStatus() == 30).findFirst();
            if (!firstP2pLoanPeriodPlan.isPresent()) {
                log.error("第一期必须还款成功");
                return false;
            }
            Optional<PeriodPlan> processLoanPeriodPlan = p2pLoanPeriodPlans.stream().filter(corePlanPeriodPlan -> corePlanPeriodPlan.getPeriod() <= period && corePlanPeriodPlan.getPeriod() > 1 && corePlanPeriodPlan.getPlanStatus() != 0).findAny();
            if (processLoanPeriodPlan.isPresent()) {
                log.error("有不是未处理的计划");
                return false;
            }
        }
        return true;
    }
}
