package com.kt.test.springbootservlet3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午10:23
 **/


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableAsync
public class Application {

    public static void main(String [] args){
        //RpcUtil.connectServer();
        SpringApplication.run(Application.class, args);
    }
}
