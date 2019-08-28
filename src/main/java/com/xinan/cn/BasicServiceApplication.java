package com.xinan.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.xinan.cn")
@MapperScan(basePackages = {
		"com.xinan.cn.p2p.report.nifa.mapper",
		"com.xinan.cn.p2p.test.repay.mapper"
})
public class BasicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicServiceApplication.class, args);
	}

}
