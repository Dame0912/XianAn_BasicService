package com.xinan.cn.p2p.test.repay.service.impl;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.bean.entities.plan.Money;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.constants.enums.ClaimStatusEnum;
import com.xinan.cn.common.constants.enums.OverdueStatusEnum;
import com.xinan.cn.common.mapper.plan.MoneyMapper;
import com.xinan.cn.common.mapper.plan.PeriodPlanMapper;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.common.utils.HttpClientUtil;
import com.xinan.cn.p2p.test.repay.service.intf.OverdueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 逾期处理
 * @Author lyq
 **/
@Slf4j
@Service
public class OverdueServiceImpl implements OverdueService {

    @Autowired
    private PeriodPlanMapper periodPlanMapper;
    @Autowired
    private PlanConfigMapper planConfigMapper;
    @Autowired
    private MoneyMapper moneyMapper;

    @Value("${xa.p2p.web.loan.check.url}")
    private String p2pWebUrl;

    @Value("${overdue.days}")
    private Integer overdueDays;

    @Override
    @Transactional
    public Map<String, Object> overdue(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        Map<String, Object> retMap = new HashMap<>();
        String planRepayDateStr = periodPlanVO.getPlanRepayDate();
        Date overdueDate = DateUtil.getAddDate(new Date(Long.parseLong(planRepayDateStr)), overdueDays);
        Long lastCheckTime = DateUtil.getDateMillis(overdueDate);
        String overdueDateStr = DateUtil.getFormatDate(overdueDate, DateUtil.DATEFORMATE_YYYY_MM_DD);
        StringBuffer loanCheckURL = new StringBuffer(p2pWebUrl);
        loanCheckURL.append(loanSimpleInfoVO.getP2pLoanId()).append("/").append(overdueDateStr);
        try {
            // 调用p2p的检查接口
            log.info("loanCheckURL：" + loanCheckURL.toString());
            String result = HttpClientUtil.doGet(loanCheckURL.toString());
            if ("ok".equals(result)) {
                log.info("请求p2p资产检查接口成功！");
                // 1、查询逾期的期数
                PeriodPlan periodPlan = new PeriodPlan();
                periodPlan.setPeriod(periodPlanVO.getPeriod());
                periodPlan.setPlanId(Long.parseLong(periodPlanVO.getP2pPlanId()));
                List<Integer> overduePeriods = new ArrayList<>();
                while (!overduePeriods.contains(periodPlanVO.getPeriod())) {
                    overduePeriods = periodPlanMapper.queryOverdueAndNoRepayWithPeriod(periodPlan);
                    TimeUnit.SECONDS.sleep(1L);
                    log.info("OverdueServiceImpl.overdue....sleep...");
                }
                log.info("逾期的期数有：" + JSON.toJSONString(overduePeriods));

                // 2、补全fd和fd投资人的money；更新逾期状态
                Long p2pPlanId = Long.parseLong(loanSimpleInfoVO.getP2pPlanId());
                Long fdPlanId = Long.parseLong(loanSimpleInfoVO.getFdPlanId());
                Long p2pInvestPlanId = loanSimpleInfoVO.allPlanIds().get(0);
                Long fdInvestPlanId = Long.parseLong(loanSimpleInfoVO.getFdInvestPlanId());
                overduePeriods.forEach(period -> {
                    // 保存房贷逾期金额
                    List<Money> p2pOverdueMoneys = moneyMapper.queryOverdueMoney(p2pPlanId, period);
                    saveMoney(fdPlanId, period, p2pOverdueMoneys);
                    // 保存房贷投资逾期金额
                    List<Money> p2pInvestOverdueMoneys = moneyMapper.queryOverdueMoney(p2pInvestPlanId, period);
                    saveMoney(fdInvestPlanId, period, p2pInvestOverdueMoneys);
                    // 删除322这个房贷金额
                    Money money = new Money();
                    money.setPlanId(fdInvestPlanId);
                    money.setPeriod(period);
                    money.setMoneyType(322);
                    moneyMapper.deleteOne(money);
                    // 将房贷和房贷投资人计划更改为逾期，代偿状态为10触发
                    updateOverduePeriodPlan(fdPlanId, period);
                    updateOverduePeriodPlan(fdInvestPlanId, period);
                });

                // 3、更新last_check_time
                Map<String, Object> param = new HashMap<>();
                param.put("lastCheckTime", lastCheckTime);
                param.put("isAllowAheadRepay", 1);
                param.put("planIds", loanSimpleInfoVO.allPlanIds());
                planConfigMapper.batchUpdateLastCheckTime(param);
            } else {
                log.error("请求p2p资产检查接口失败！");
                retMap.put("isSuccess", false);
            }
        } catch (Exception e) {
            throw new RuntimeException("OverdueServiceImpl.overdue异常：" + e.getMessage());
        }
        retMap.put("isSuccess", true);
        return retMap;
    }

    /**
     * 更新计划为逾期
     *
     * @param planId 计划id
     * @param period 期数
     */
    private void updateOverduePeriodPlan(Long planId, Integer period) {
        PeriodPlan periodPlan = new PeriodPlan();
        periodPlan.setPlanId(planId);
        periodPlan.setPeriod(period);
        periodPlan.setIsOverdue(OverdueStatusEnum.IS_OVERDUE.getCode());
        periodPlan.setClaimStatus(ClaimStatusEnum.CLAIM_TRIGGERED.getCode());
        periodPlanMapper.updateOneByPlanId(periodPlan);
    }

    /**
     * 保存金额信息
     *
     * @param otherPlanId 需要保存的planId
     * @param period      期数
     * @param moneys      依赖的money
     */
    private void saveMoney(Long otherPlanId, Integer period, List<Money> moneys) {
        moneys.forEach(money -> {
            money.setPlanId(otherPlanId);
            money.setPeriod(period);
            money.setId(null);
            moneyMapper.insertOne(money);
        });
    }
}
