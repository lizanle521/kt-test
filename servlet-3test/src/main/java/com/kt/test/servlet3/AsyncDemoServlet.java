package com.kt.test.servlet3;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 上午10:58
 **/

@WebServlet(urlPatterns = "/demo",asyncSupported = true,
loadOnStartup = 1,
initParams = {
        @WebInitParam(name = "msg",value = "222")
})
public class AsyncDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print("进入servlet 时间: "+new Date() + ".");

        AsyncContext context = req.getAsyncContext();

        //添加异步监听
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onTimeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onStartAsync");
            }
        });

        //子线程处理业务,并负责输出,主线成退出
        new Thread(new Executor(context)).start();
        out.println("结束Servlet的时间：" + new Date() + ".");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    class Executor implements Runnable{

        private AsyncContext context;

        public Executor(AsyncContext context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                //等待十秒钟，以模拟业务方法的执行
                Thread.sleep(10000);
                PrintWriter out = context.getResponse().getWriter();
                out.println("业务处理完毕的时间：" + new Date() + ".");
                out.flush();
                context.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
