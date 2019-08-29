package com.xinan.cn.common.controller.plan;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.service.plan.intf.PeriodPlanManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 计划信息
 * @Author lyq
 **/
@Slf4j
@Controller
@RequestMapping("/plan/")
public class CorePlanController {
    @Autowired
    private PeriodPlanManageService periodPlanManageService;

    @ResponseBody
    @RequestMapping("periodPlanSimpleInfo.do")
    public PeriodPlanVO loanSimpleInfo(HttpServletRequest request) {
        Long skuId = Long.valueOf(request.getParameter("skuId"));
        PeriodPlanVO periodPlanVO = periodPlanManageService.getPeriodPlanVO(skuId);
        log.info("periodPlanVO:" + JSON.toJSONString(periodPlanVO));
        return periodPlanVO;
    }
}
