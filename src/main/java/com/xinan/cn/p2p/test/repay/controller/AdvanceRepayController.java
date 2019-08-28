package com.xinan.cn.p2p.test.repay.controller;

import com.google.common.collect.ImmutableMap;
import com.xinan.cn.p2p.test.repay.service.intf.AdvanceRepayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 提前还款
 */
@Slf4j
@Controller
@RequestMapping("/test/repay/")
public class AdvanceRepayController {

    @Autowired
    private AdvanceRepayService advanceRepayService;

    @RequestMapping("advaneRepay.do")
    public void advaneRepay(HttpServletRequest request){
        //String skuId = request.getParameter("skuId");//标的
        //String period = request.getParameter("period");//期数
        String skuId = "1165824340444418048";//标的
        String period = "2";//期数
        advanceRepayService.advanceRepay(ImmutableMap.of("skuId", skuId, "period", period));




        // 查询还款计划




        //前面需要还款成功

        //查询p2p的planId
        //查询p2p投资人的planId
        //查询房贷的planId
        //查询房贷投资人的planId

        //返回到投资人页面

        //本页面展示计划状态
    }
}
