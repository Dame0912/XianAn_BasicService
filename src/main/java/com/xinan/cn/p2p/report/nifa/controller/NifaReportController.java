package com.xinan.cn.p2p.report.nifa.controller;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;
import com.xinan.cn.p2p.report.nifa.service.intf.NifaReportQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/report/nifa/")
public class NifaReportController {

    @Autowired
    private NifaReportQueryService reportQueryService;

    @RequestMapping("nifaIndex.do")
    public String nifaIndex() {
        return "p2p/report/nifa/nifaReportQuery";
    }

    @RequestMapping("nifaReportQuery.do")
    @ResponseBody
    public Map<String, Object> queryReportInfo(NifaReportQueryParamVO requestParam) {
        log.info("根据条件查询报送信息开始,requestParam:" + JSON.toJSONString(requestParam));
        Map<String, Object> resultMap = reportQueryService.queryReportInfoPage(requestParam);
        log.info("根据条件查询报送信息结束,result:" + JSON.toJSONString(resultMap));
        return resultMap;
    }

}
