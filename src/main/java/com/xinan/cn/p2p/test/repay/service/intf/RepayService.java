package com.xinan.cn.p2p.test.repay.service.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;

import java.util.Map;

/**
 * 还款
 */
public interface RepayService {
    Map<String, Object> repay(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO);
}
