package com.xinan.cn.p2p.test.repay.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
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
 * 逾期处理
 *
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
    public Map<String, Object> overdue(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        String planRepayDateStr = periodPlanVO.getPlanRepayDate();
        Date overdueDate = DateUtil.getAddDate(new Date(Long.parseLong(planRepayDateStr)), overdueDays);
        Long lastCheckTime = DateUtil.getDateMillis(overdueDate);
        String overdueDateStr = DateUtil.getFormatDate(overdueDate, DateUtil.DATEFORMATE_YYYY_MM_DD);
        StringBuilder loanCheckURL = new StringBuilder(p2pWebUrl);
        loanCheckURL.append(loanSimpleInfoVO.getP2pLoanId()).append("/").append(overdueDateStr);
        log.info("loanCheckURL：" + loanCheckURL.toString());
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 调用p2p的检查接口
            List<Integer> overduePeriods = getOverduePeriods(loanCheckURL.toString(), periodPlanVO, loanSimpleInfoVO);
            // 订正房贷的逾期数据等
            revisalFdData(loanSimpleInfoVO, lastCheckTime, overduePeriods);
        } catch (Exception e) {
            log.error("OverdueServiceImpl.overdue异常：" + e.getMessage());
            retMap.put("isSuccess", false);
        }
        retMap.put("isSuccess", true);
        return retMap;
    }

    private List<Integer> getOverduePeriods(String loanCheckURL, PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        List<Integer> overduePeriods = null;
        try {
            String result = HttpClientUtil.doGet(loanCheckURL);
            if ("ok".equals(result)) {
                // 1、查询逾期的期数
                PeriodPlan periodPlan = new PeriodPlan();
                periodPlan.setPeriod(periodPlanVO.getPeriod());
                periodPlan.setPlanId(Long.parseLong(periodPlanVO.getP2pPlanId()));
                // overduePeriods = periodPlanMapper.queryOverdueAndNoRepayWithPeriod(periodPlan);// 有时候loanCheck请求一次不行，为了保险，请求两次
                int count = 0;
                while (null == overduePeriods || !overduePeriods.contains(periodPlanVO.getPeriod())) {
                    log.info("OverdueServiceImpl.overdue....sleep..." + count);
                    TimeUnit.SECONDS.sleep(1L);
                    overduePeriods = overduePeriods(loanCheckURL, periodPlan);
                    if (count > 5) {
                        throw new RuntimeException();
                    }
                    count++;
                }
                log.info("逾期的期数有：" + JSON.toJSONString(overduePeriods));
            }
        } catch (Exception e) {
            // 还原
            List<Long> allPlanIds = loanSimpleInfoVO.allPlanIds();
            List<Long> fdPlanIds = Arrays.asList(Long.parseLong(loanSimpleInfoVO.getFdPlanId()), Long.parseLong(loanSimpleInfoVO.getFdInvestPlanId()));
            allPlanIds.removeAll(fdPlanIds);
            Map<String, Object> planConfigParam = ImmutableMap.of("lastCheckTime", periodPlanVO.getFdLastCheckTime(), "planIds", allPlanIds);
            planConfigMapper.batchUpdateLastCheckTime(planConfigParam);
            Map<String, Object> periodPlanParam = ImmutableMap.of("isOverdue", OverdueStatusEnum.NOT_OVERDUE, "claimStatus", ClaimStatusEnum.CLAIM_NOT_TRIGGER, "planIds", allPlanIds, "periods", Collections.singletonList(periodPlanVO.getPeriod()));
            periodPlanMapper.batchUpdateOverdueAndClaim(periodPlanParam);
        }
        return overduePeriods;
    }

    private List<Integer> overduePeriods(String loanCheckURL, PeriodPlan periodPlan) {
        String result = HttpClientUtil.doGet(loanCheckURL);
        if ("ok".equals(result)) {
            return periodPlanMapper.queryOverdueAndNoRepayWithPeriod(periodPlan);
        }
        return null;
    }

    /**
     * 修正逾期的房贷数据
     */
    @Transactional
    public void revisalFdData(LoanSimpleInfoVO loanSimpleInfoVO, Long lastCheckTime, List<Integer> overduePeriods) {
        Long p2pPlanId = Long.parseLong(loanSimpleInfoVO.getP2pPlanId());
        Long fdPlanId = Long.parseLong(loanSimpleInfoVO.getFdPlanId());
        Long p2pInvestPlanId = loanSimpleInfoVO.allPlanIds().get(0);
        Long fdInvestPlanId = Long.parseLong(loanSimpleInfoVO.getFdInvestPlanId());
        // 1、补全fd和fd投资人的money；更新逾期状态
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
            // 将房贷和房贷投资人计划更改为逾期，代偿状态为0未触发代偿
            updateOverduePeriodPlan(loanSimpleInfoVO.allPlanIds(), period);
        });

        // 2、更新last_check_time
        Map<String, Object> param = new HashMap<>();
        param.put("lastCheckTime", lastCheckTime);
        param.put("isAllowAheadRepay", 1);
        param.put("planIds", loanSimpleInfoVO.allPlanIds());
        planConfigMapper.batchUpdateLastCheckTime(param);
    }

    /**
     * 更新计划为逾期
     *
     * @param planIds 计划ids
     * @param period  期数
     */
    private void updateOverduePeriodPlan(List<Long> planIds, Integer period) {
        planIds.forEach(planId -> {
            PeriodPlan periodPlan = new PeriodPlan();
            periodPlan.setPlanId(planId);
            periodPlan.setPeriod(period);
            periodPlan.setIsOverdue(OverdueStatusEnum.IS_OVERDUE.getCode());
            periodPlan.setClaimStatus(ClaimStatusEnum.CLAIM_TRIGGERED.getCode());
            periodPlanMapper.updateOneByPlanId(periodPlan);
        });
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
