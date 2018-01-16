package com.kt.test.springbootservlet3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Callable;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午10:55
 **/
@Configuration
public class MvcConfig  extends WebMvcConfigurerAdapter{


    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(10*1000L); //tomcat默认10秒
        configurer.setTaskExecutor(mvcTaskExecutor());//所借助的TaskExecut
        //super.configureAsyncSupport(configurer);


        //add callable listener
        configurer.registerCallableInterceptors(new CallableProcessingInterceptor() {
            @Override
            public <T> void beforeConcurrentHandling(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {

            }

            @Override
            public <T> void preProcess(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {

            }

            @Override
            public <T> void postProcess(NativeWebRequest nativeWebRequest, Callable<T> callable, Object o) throws Exception {

            }

            @Override
            public <T> Object handleTimeout(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
                return null;
            }

            @Override
            public <T> void afterCompletion(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
                System.out.println("afterCompletion");
            }
        });

    }

    @Bean
    public ThreadPoolTaskExecutor mvcTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(100);
        executor.setMaxPoolSize(25);
        executor.setKeepAliveSeconds(20);
        executor.setThreadNamePrefix("mvcAsyncThread-");
        return executor;

    }

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/async").setViewName("/async");
    }*/
}
