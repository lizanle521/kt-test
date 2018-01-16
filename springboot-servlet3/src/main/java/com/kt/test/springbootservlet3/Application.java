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
 *
 * <Connector port="1601" asyncTimeout="10000" acceptCount="10240" maxConnections="10240" acceptorThreadCount="1"  minSpareThreads="1" maxThreads="1" redirectPort="8443" processorCache="1024" URIEncoding="UTF-8" protocol="org.apache.coyote.http11.Http11NioProtocol" enableLookups="false"/>
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
