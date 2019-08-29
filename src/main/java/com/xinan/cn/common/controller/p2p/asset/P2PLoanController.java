package com.xinan.cn.common.controller.p2p.asset;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.service.p2p.asset.intf.P2PLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 资产信息
 * @Author lyq
 **/
@Slf4j
@Controller
@RequestMapping("/p2p/loan/")
public class P2PLoanController {

    @Autowired
    private P2PLoanService p2PLoanServiceIntf;

    @ResponseBody
    @RequestMapping("loanSimpleInfo.do")
    public LoanSimpleInfoVO loanSimpleInfo(HttpServletRequest request) {
        Long skuId = Long.valueOf(request.getParameter("skuId"));
        LoanSimpleInfoVO loanSimpleInfoVO = p2PLoanServiceIntf.getLoanSimpleInfo(skuId);
        log.info("loanSimpleInfoVO:" + JSON.toJSONString(loanSimpleInfoVO));
        return loanSimpleInfoVO;
    }
}
