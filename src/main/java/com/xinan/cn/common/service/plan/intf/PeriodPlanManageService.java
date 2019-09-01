package com.xinan.cn.common.service.plan.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;

import java.util.List;

public interface PeriodPlanManageService {

    /**
     * 查询出页面展示的计划信息
     */
    List<PeriodPlanVO> getPeriodPlanVO(LoanSimpleInfoVO loanSimpleInfoVO);
}
