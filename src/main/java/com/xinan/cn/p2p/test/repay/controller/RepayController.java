package com.xinan.cn.p2p.test.repay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
