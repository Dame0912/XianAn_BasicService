package com.xinan.cn.p2p.test.repay.service.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.constants.enums.OverdueStatusEnum;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.test.repay.service.intf.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 还款通用处理
 * @Author lyq
 **/
@Service
public class RepayCommonServiceImpl implements RepayService {

    @Autowired
    private PlanConfigMapper planConfigMapper;

    @Override
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

    @Transactional
    public void updateLastCheckTime(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        Long lastCheckTime;
        // 逾期
        Integer isOverdue = periodPlanVO.getP2pIsOverdue();
        if (OverdueStatusEnum.IS_OVERDUE.getCode() == isOverdue) {
            return;
        } else {
            String repayDate = periodPlanVO.getPlanRepayDate();
            lastCheckTime = DateUtil.getDateMillis(DateUtil.getAddDate(new Date(Long.parseLong(repayDate)), -1));
        }
        List<Long> allPlanIds = loanSimpleInfoVO.allPlanIds();

        Map<String, Object> param = new HashMap<>();
        param.put("lastCheckTime", lastCheckTime);
        param.put("isAllowAheadRepay", 1);
        param.put("planIds", allPlanIds);
        int count = planConfigMapper.batchUpdateLastCheckTime(param);
        if (count != allPlanIds.size()) {
            throw new RuntimeException("AbstractRepay.updateLastCheckTime异常,需要更新的条目数:" + allPlanIds.size() + ", 实际更新:" + count);
        }
    }

    /**
     * 返回参数
     * 子类实现
     */
    public Map<String, Object> repayRetParam(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        return null;
    }
}
