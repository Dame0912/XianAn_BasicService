package com.xinan.cn.p2p.test.repay.controller;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 还款
 *
 * @Author lyq
 **/
@Slf4j
@Controller
@RequestMapping("/test/repay/")
public class RepayController {

    @RequestMapping("repayIndex.do")
    public String repayIndex() {
        return "p2p/test/repay/repay";
    }

    @ResponseBody
    @RequestMapping("ownRepay.do")
    public String repayIndex(PeriodPlanVO periodPlanVO) {
        log.info("periodPlanVO:" + JSON.toJSONString(periodPlanVO));

        return "abc";
    }
}
