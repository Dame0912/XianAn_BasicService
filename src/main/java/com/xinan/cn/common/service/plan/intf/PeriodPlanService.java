package com.xinan.cn.common.service.plan.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.AllPlanInfo;

public interface PeriodPlanService {

    /**
     * 获取所有的还款计划
     */
    AllPlanInfo getAllPlan(Long skuId, LoanSimpleInfoVO... loanSimpleInfoVO);

}
