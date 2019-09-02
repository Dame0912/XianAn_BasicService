package com.xinan.cn.p2p.test.repay.service.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.constants.enums.OverdueStatusEnum;
import com.xinan.cn.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代偿还款
 */
@Slf4j
@Service("repayCompenServiceImpl")
public class RepayCompenServiceImpl extends AbstractRepay {

    @Value("${xa.compen.repay.url}")
    private String compenRepayUrl;

    @Override
    public Long createLastCheckTime(Date planRepayDate) {
        return DateUtil.getDateMillis(planRepayDate);//代偿日=计划还款日
    }

    @Override
    public Map<String, Object> repayRetParam(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        Date planRepayDate = new Date(Long.parseLong(periodPlanVO.getPlanRepayDate()));
        String planRepayDateStr = DateUtil.getFormatDate(planRepayDate, DateUtil.DATEFORMATE_YYYYMMDD);
        String repayTime;
        if (OverdueStatusEnum.IS_OVERDUE.getCode() == periodPlanVO.getP2pIsOverdue()) {
            String checkTime = periodPlanVO.getP2pLastCheckTime();
            repayTime = DateUtil.getFormatDate(new Date(Long.parseLong(checkTime)), DateUtil.DATEFORMATE_YYYYMMDD);
        } else {
            repayTime = planRepayDateStr;
        }

        // 提示信息
        StringBuilder tipMsg = new StringBuilder();
        tipMsg.append("<font color='red' style='font-weight:bold;'>还款时间：</font>");
        tipMsg.append(repayTime);
        tipMsg.append("<br><br>");
        tipMsg.append("<font style='font-weight:bold; font-style:italic;'>查询代偿信息</font>");
        tipMsg.append("<br><br>");
        tipMsg.append("<font style='font-weight:bold;'>借款ID：</font>");
        tipMsg.append(loanSimpleInfoVO.getP2pLoanId());
        tipMsg.append("<br>");
        tipMsg.append("<font style='font-weight:bold;'>项目类型：</font>");
        tipMsg.append(loanSimpleInfoVO.getLoanName());
        tipMsg.append("<br>");
        tipMsg.append("<font style='font-weight:bold;'>查询时间：</font>");
        tipMsg.append(planRepayDateStr);

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("redirectUrl", compenRepayUrl);
        retMap.put("tipMsg", tipMsg.toString());
        return retMap;
    }
}
