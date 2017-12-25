package com.kt.test.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * : 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
 *   HystrixBadRequestException用在非法参数或非系统故障异常等不应触发回退逻辑的场景。
 */
public class FallBackTest  extends HystrixCommand<String>{

    private String name;

    public FallBackTest(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                .andCommandPropertiesDefaults(
                        //设置业务执行超时就进行getFallback 操作
                        HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)
                )

        );
    }


    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }

    public static void main(String[] args) throws Exception{
        FallBackTest command = new FallBackTest("test-Fallback");
        String result = command.execute();
        System.out.println(result);
    }
}
