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
 * 本人还款
 */
@Slf4j
@Service("repayOwnServiceImpl")
public class RepayOwnServiceImpl extends AbstractRepay {

    @Value("${xa.own.repay.url}")
    private String ownRepayUrl;

    @Override
    public Long createLastCheckTime(Date planRepayDate) {
        return DateUtil.getDateMillis(DateUtil.getAddDate(planRepayDate, -1));
    }

    @Override
    public Map<String, Object> repayRetParam(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        String repayTime;
        if (OverdueStatusEnum.IS_OVERDUE.getCode() == periodPlanVO.getP2pIsOverdue()) {
            String checkTime = periodPlanVO.getP2pLastCheckTime();
            repayTime = checkTime.substring(0, checkTime.length() - 3);
        } else {
            repayTime = String.valueOf(DateUtil.getCurSeconds());
        }

        // 还款地址
        StringBuilder redirectUrl = new StringBuilder(ownRepayUrl);
        redirectUrl.append(loanSimpleInfoVO.getLoanBaseId());

        // 提示信息
        StringBuilder tipMsg = new StringBuilder();
        tipMsg.append("<font style='font-weight:bold;'>还款时间：</font>");
        tipMsg.append(repayTime);
        tipMsg.append("<br><br>");
        tipMsg.append("<font color='red' style='font-weight:bold; font-style:italic;'>请使用借款人账号登录还款！！</font>");
        tipMsg.append("<br><br>");
        tipMsg.append("<font style='font-weight:bold;'>用户名：</font>");
        tipMsg.append(loanSimpleInfoVO.getUserName());

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("redirectUrl", redirectUrl.toString());
        retMap.put("tipMsg", tipMsg.toString());
        return retMap;
    }

}
