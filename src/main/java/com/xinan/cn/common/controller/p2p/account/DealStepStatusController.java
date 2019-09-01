package com.xinan.cn.common.controller.p2p.account;

import com.xinan.cn.common.bean.entities.p2p.account.DealStepStatus;
import com.xinan.cn.common.service.p2p.account.intf.DealStepStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description TODO
 * @Author lyq
 * @Date 2019/9/1 16:20
 **/
@Slf4j
@Controller
@RequestMapping("/p2p/account/")
public class DealStepStatusController {

    @Autowired
    private DealStepStatusService dealStepStatusService;

    @ResponseBody
    @RequestMapping("dealStepStatusInfo.do")
    public List<DealStepStatus> dealStepStatusInfo(HttpServletRequest request) {
        Long skuId = Long.parseLong(request.getParameter("skuId"));
        List<DealStepStatus> dealStepStatusList = dealStepStatusService.getDealStepStatusInfoBySkuId(skuId);
        return dealStepStatusList;
    }
}
