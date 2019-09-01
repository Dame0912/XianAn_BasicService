package com.xinan.cn.p2p.test.repay.service.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;

import java.util.Map;

/**
 * 逾期处理
 */
public interface OverdueService {
    Map<String, Object> overdue(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO);
}
