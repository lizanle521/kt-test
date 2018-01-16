package com.kt.test.springbootservlet3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午10:27
 **/

@Controller
public class TestController {

    @RequestMapping("/async")
    @ResponseBody
    public Callable<String> callable() {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return () -> {
            Thread.sleep(3 * 1000L);
            return "小单 - " + System.currentTimeMillis();
        };
    }


    @RequestMapping("/hello")
    public Callable<String> hello() {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return () -> {
           // Thread.sleep(3 * 1000L);
            System.out.println(Thread.currentThread().getName());
            return "index";
        };
    }
}
