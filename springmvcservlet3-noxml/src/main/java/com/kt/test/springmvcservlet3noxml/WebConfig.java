package com.kt.test.springmvcservlet3noxml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午9:59
 * proxy dispatcher-servlet.xml
 **/

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.kt.test.springmvcservlet3noxml")
public class WebConfig {


    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
