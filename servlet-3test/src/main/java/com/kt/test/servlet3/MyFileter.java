package com.kt.test.servlet3;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.xml.ws.WebFault;
import java.io.IOException;

/**
 * @User: jufeng
 * @Date: 18-1-16
 * @Time: 下午2:15
 **/

@WebFilter(filterName = "haa",urlPatterns = "/*",dispatcherTypes ={DispatcherType.REQUEST,DispatcherType.FORWARD} )
public class MyFileter  implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
