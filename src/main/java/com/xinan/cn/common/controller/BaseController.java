package com.xinan.cn.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Value("${env}")
    private String env;

    @RequestMapping(value = {"/", "index"})
    public String index(HttpServletRequest request) {
        System.out.println(env);
        request.setAttribute("ENV", env);
        return "index";
    }
}
