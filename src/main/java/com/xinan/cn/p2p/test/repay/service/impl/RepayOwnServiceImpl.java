package com.xinan.cn.p2p.test.repay.service.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.constants.enums.OverdueStatusEnum;
import com.xinan.cn.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 本人还款
 */
@Slf4j
@Service("repayOwnServiceImpl")
public class RepayOwnServiceImpl extends RepayCommonServiceImpl {

    @Value("${xa.p2p.web.url}")
    private String p2pWebUrl;

    @Override
    public Map<String, Object> repayRetParam(PeriodPlanVO periodPlanVO, LoanSimpleInfoVO loanSimpleInfoVO) {
        Map<String, Object> retMap = new HashMap<>();
        String repayTime;
        if (OverdueStatusEnum.IS_OVERDUE.getCode() == periodPlanVO.getP2pIsOverdue()) {
            String checkTime = periodPlanVO.getP2pLastCheckTime();
            repayTime = checkTime.substring(0, checkTime.length() - 3);
        } else {
            repayTime = String.valueOf(DateUtil.getCurSeconds());
        }

        // 还款地址
        StringBuffer redirectUrl = new StringBuffer(p2pWebUrl);
        redirectUrl.append("/account/loan_detail/");
        redirectUrl.append(loanSimpleInfoVO.getLoanBaseId());

        // 提示信息
        StringBuffer tipMsg = new StringBuffer();
        tipMsg.append("<font style='font-weight:bold;'>还款时间：</font>");
        tipMsg.append(repayTime);
        tipMsg.append("<br><br>");
        tipMsg.append("<font color='red' style='font-weight:bold; font-style:italic;'>登陆借款人账号</font>");
        tipMsg.append("<br><br>");
        tipMsg.append("<font style='font-weight:bold;'>用户名：</font>");
        tipMsg.append(loanSimpleInfoVO.getUserName());

        retMap.put("redirectUrl", redirectUrl.toString());
        retMap.put("tipMsg", tipMsg.toString());
        return retMap;
    }

}
