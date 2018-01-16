package com.kt.test.springmvcservlet3;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping(value = "/hello")
	public Callable<String> hello(final HttpServletRequest request,
                                  final HttpServletResponse response) {
		System.out.println("in..." + Thread.currentThread().getName());
		System.out.println(System.currentTimeMillis());
		return new Callable<String>() {
			public String call() throws Exception {
				try {
				    //request.getServletContext()
                    //request.getAsyncContext();
					System.out.println(1);
					if (!response.isCommitted()) {
						System.out.println("2222"
								+ Thread.currentThread().getName());
						response.setContentType("text/plain;charset=utf-8");
						response.getWriter().write("hello");
						response.getWriter().close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}
}
