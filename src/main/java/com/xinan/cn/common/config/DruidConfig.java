package com.xinan.cn.common.config;

import java.util.*;

import javax.sql.DataSource;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid监控配置
 *
 * @author lyq
 */

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        DruidDataSource druidDataSource = new DruidDataSource();
        List filterList = new ArrayList<>();
        filterList.add(wallFilter());
        druidDataSource.setProxyFilters(filterList);
        return druidDataSource;
    }

    // 配置Druid的监控
    // 1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");// 登陆名
        initParams.put("loginPassword", "root");// 密码
        initParams.put("allow", "");// 默认就是允许所有访问
        initParams.put("deny", "");// 拒绝相对应的ip访问
        bean.setInitParameters(initParams);
        return bean;
    }

    // 2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        // 设置filter初始化参数、
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");// 排除静态资源和请求
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));// 拦截所有请求
        return bean;
    }


    // 下面为了保证可以实现批量操作
    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean(name = "wallConfig")
    public WallConfig wallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
        return wallConfig;
    }
}
