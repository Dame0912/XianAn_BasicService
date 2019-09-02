package com.xinan.cn.p2p.test.repay.controller;

import com.alibaba.fastjson.JSONObject;
import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.p2p.test.repay.service.impl.AbstractRepay;
import com.xinan.cn.p2p.test.repay.service.intf.OverdueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 还款
 *
 * @Author lyq
 **/
@Slf4j
@Controller
@RequestMapping("/test/repay/")
public class RepayController {

    @Autowired
    @Qualifier("repayOwnServiceImpl")
    private AbstractRepay repayOwnServiceImpl;//本人还款

    @Autowired
    @Qualifier("repayCompenServiceImpl")
    private AbstractRepay repayCompenServiceImpl;//代偿还款

    @Autowired
    private OverdueService overdueService;

    @RequestMapping("repayIndex.do")
    public String repayIndex() {
        return "p2p/test/repay/repay";
    }

    /**
     * 本人还款
     */
    @ResponseBody
    @RequestMapping("ownRepay.do")
    public Map<String, Object> ownRepay(HttpServletRequest request) {
        log.info("RepayController.ownRepay,个人还款开始");
        String periodPlanStr = request.getParameter("periodPlanVO");
        String loanSimpleInfoStr = request.getParameter("loanSimpleInfoVO");
        PeriodPlanVO periodPlanVO = JSONObject.parseObject(periodPlanStr, PeriodPlanVO.class);
        LoanSimpleInfoVO loanSimpleInfoVO = JSONObject.parseObject(loanSimpleInfoStr, LoanSimpleInfoVO.class);
        Map<String, Object> resultMap = buildReturnMap(repayOwnServiceImpl.repay(periodPlanVO, loanSimpleInfoVO));
        log.info("RepayController.repayIndex,个人还款结束");
        return resultMap;
    }

    /**
     * 逾期
     */
    @ResponseBody
    @RequestMapping("overdue.do")
    public Map<String, Object> overdue(HttpServletRequest request) {
        log.info("RepayController.overdue,逾期处理开始");
        String periodPlanStr = request.getParameter("periodPlanVO");
        String loanSimpleInfoStr = request.getParameter("loanSimpleInfoVO");
        PeriodPlanVO periodPlanVO = JSONObject.parseObject(periodPlanStr, PeriodPlanVO.class);
        LoanSimpleInfoVO loanSimpleInfoVO = JSONObject.parseObject(loanSimpleInfoStr, LoanSimpleInfoVO.class);
        Map<String, Object> resultMap = buildReturnMap(overdueService.overdue(periodPlanVO, loanSimpleInfoVO));
        log.info("RepayController.overdue,逾期处理结束");
        return resultMap;
    }

    /**
     * 代偿还款
     */
    @ResponseBody
    @RequestMapping("compenRepay.do")
    public Map<String, Object> compenRepay(HttpServletRequest request) {
        log.info("RepayController.compenRepay,代偿处理开始");
        String periodPlanStr = request.getParameter("periodPlanVO");
        String loanSimpleInfoStr = request.getParameter("loanSimpleInfoVO");
        PeriodPlanVO periodPlanVO = JSONObject.parseObject(periodPlanStr, PeriodPlanVO.class);
        LoanSimpleInfoVO loanSimpleInfoVO = JSONObject.parseObject(loanSimpleInfoStr, LoanSimpleInfoVO.class);
        Map<String, Object> resultMap = buildReturnMap(repayCompenServiceImpl.repay(periodPlanVO, loanSimpleInfoVO));
        log.info("RepayController.compenRepay,代偿处理结束");
        return resultMap;
    }

    private Map<String, Object> buildReturnMap(Map<String, Object> srcMap) {
        Map<String, Object> resultMap;
        try {
            resultMap = srcMap;
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("isSuccess", false);
        }
        return resultMap;
    }
}
