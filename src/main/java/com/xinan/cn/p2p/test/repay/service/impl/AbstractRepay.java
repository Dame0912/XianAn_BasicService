package com.xinan.cn.p2p.test.repay.service.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.constants.enums.OverdueStatusEnum;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
import com.xinan.cn.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractRepay {

    public Map<String, Object> repay(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("isSuccess", true);
        try {
            // 1、将last_check_time修改
            updateLastCheckTime(periodPlanVO, loanSimpleInfoVO);
            // 2、逾期和提前的时间不同, url不同
            retMap.putAll(repayRetParam(periodPlanVO, loanSimpleInfoVO));
        } catch (Exception e) {
            retMap.put("isSuccess", false);
        }
        return retMap;
    }

    private void updateLastCheckTime(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        if (OverdueStatusEnum.IS_OVERDUE.getCode() == periodPlanVO.getP2pIsOverdue()) { // 逾期
            return;
        }
        Date planRepayDate = new Date(Long.parseLong(periodPlanVO.getPlanRepayDate()));
        Long lastCheckTime = createLastCheckTime(planRepayDate);
        List<Long> allPlanIds = loanSimpleInfoVO.allPlanIds();

        Map<String, Object> param = new HashMap<>();
        param.put("lastCheckTime", lastCheckTime);
        param.put("isAllowAheadRepay", 1);
        param.put("planIds", allPlanIds);

        TransactionTemplate transactionTemplate = SpringContextUtil.getBean(TransactionTemplate.class);
        Boolean isSuccess = transactionTemplate.execute(status -> {
            Boolean result = true;
            try {
                PlanConfigMapper planConfigMapper = SpringContextUtil.getBean(PlanConfigMapper.class);
                int count = planConfigMapper.batchUpdateLastCheckTime(param);
                if (count != allPlanIds.size()) {
                    throw new RuntimeException("AbstractRepay.updateLastCheckTime异常,需要更新的条目数:" + allPlanIds.size() + ", 实际更新:" + count);
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                result = false;
            }
            return result;
        });
        log.info("批量更新last_check_time,result:" + isSuccess);
    }

    /**
     * 计算last_check_time
     */
    public abstract Long createLastCheckTime(Date planRepayDate);

    /**
     * 构建返回参数
     */
    public abstract Map<String, Object> repayRetParam(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO);
}
