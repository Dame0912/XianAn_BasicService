package com.xinan.cn.common.controller.plan;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.dto.plan.PeriodPlanVO;
import com.xinan.cn.common.service.plan.intf.PeriodPlanManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 计划信息展示
 *
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
    public List<PeriodPlanVO> loanSimpleInfo(LoanSimpleInfoVO loanSimpleInfoVO) {
        log.info(":" + JSON.toJSONString(loanSimpleInfoVO));
        List<PeriodPlanVO> periodPlanVOList = periodPlanManageService.getPeriodPlanVO(loanSimpleInfoVO);
        log.info("periodPlanVOList:" + JSON.toJSONString(periodPlanVOList));
        return periodPlanVOList;
    }
}
