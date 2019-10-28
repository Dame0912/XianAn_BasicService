package com.xinan.cn.p2p.report.nifa.controller;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;
import com.xinan.cn.p2p.report.nifa.bean.dto.NifaReqRecordDataDTO;
import com.xinan.cn.p2p.report.nifa.service.intf.NifaReportQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("nifaPrjInfoQuery.do")
    @ResponseBody
    public Map<String, Object> queryPrjInfo(NifaReportQueryParamVO requestParam) {
        log.info("查询报送项目信息开始,requestParam:" + JSON.toJSONString(requestParam));
        Map<String, Object> resultMap = reportQueryService.queryPrjInfoPage(requestParam);
        log.info("查询报送项目信息结束,result:" + JSON.toJSONString(resultMap));
        return resultMap;
    }

    @RequestMapping("nifaReqRecordQuery.do")
    @ResponseBody
    public List<NifaReqRecordDataDTO> queryReqRecordInfo(HttpServletRequest request) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("prjNum", request.getParameter("prjNum"));
        reqMap.put("status", request.getParameter("status"));
        reqMap.put("startTime", request.getParameter("startTime"));
        reqMap.put("endTime", request.getParameter("endTime"));
        log.info("查询报送请求记录开始,requestParam:" + JSON.toJSONString(reqMap));
        List<NifaReqRecordDataDTO> resultList = reportQueryService.queryReqRecordPage(reqMap);
        log.info("查询报送请求记录结束,result:" + JSON.toJSONString(resultList));
        return resultList;
    }

}
