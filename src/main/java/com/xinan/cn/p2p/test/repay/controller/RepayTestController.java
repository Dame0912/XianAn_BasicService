package com.xinan.cn.p2p.test.repay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description TODO
 * @Author lyq
 * @Date 2019/8/28 21:44
 **/

@Slf4j
@Controller
@RequestMapping("/test/repay/")
public class RepayTestController {

    @RequestMapping("repayIndex.do")
    public String repayIndex() {
        return "systemTest/repay/repay";
    }
}
