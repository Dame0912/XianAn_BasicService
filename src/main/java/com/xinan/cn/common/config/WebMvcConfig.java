package com.xinan.cn.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Resource(name = "thymeleafViewResolver")
    private ThymeleafViewResolver thymeleafViewResolver;

    @Value("${env}")
    private String currentEnvironment;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            Map<String, Object> param = new HashMap<>(8);
            param.put("currentEnvironment", currentEnvironment);//当前环境
            thymeleafViewResolver.setStaticVariables(param);
        }
        super.configureViewResolvers(registry);
    }
}
