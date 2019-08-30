package com.xinan.cn.common.service.plan.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.bean.entities.plan.PeriodPlan;

import java.util.List;

public interface PeriodPlanManageService {

    /**
     * 检查是否允许还款到指定期数
     */
    boolean checkIsAllowRepay(List<PeriodPlan> periodPlans, Integer period);

    /**
     * 查询出页面展示的计划信息
     */
    List<PeriodPlanVO> getPeriodPlanVO(LoanSimpleInfoVO loanSimpleInfoVO);
}
