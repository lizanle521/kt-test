package com.kt.test.servlet3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午2:17
 *
 **/

@WebListener
public class MyServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //在监听中初始化线程池
        // create the thread pool
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 50000L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        sce.getServletContext().setAttribute("executor",
                executor);
        System.out.println("init servlet context");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) sce
                .getServletContext().getAttribute("executor");
        executor.shutdown();
        System.out.println("destroy servlet container");
    }
}
