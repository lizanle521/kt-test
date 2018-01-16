package com.kt.test.servlet3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午2:17
 **/

@WebListener
public class NyListener  implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("init servlet context");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("destroy servlet container");
    }
}
