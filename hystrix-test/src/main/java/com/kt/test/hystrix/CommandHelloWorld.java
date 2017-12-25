package com.kt.test.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * author: jufeng
 *  note:异步调用使用 command.queue()get(timeout, TimeUnit.MILLISECONDS);
 *  同步调用使用command.execute() 等同于 command.queue().get();
 */
public class CommandHelloWorld extends HystrixCommand<String>{

    private String name;


    protected CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    protected String run() throws Exception {
        return "Hello "+ name;
    }


    public static void main(String [] a){

        //同步执行
        System.out.println(new CommandHelloWorld("ZhaoJun").execute());

        CommandHelloWorld commandHelloWorld = new CommandHelloWorld("Jufeng");
        Future<String> future = commandHelloWorld.queue();//异步执行
        try {
            //此处的超时时间不能超过command的定义的超时间，默认1秒
            System.out.println(future.get(100, TimeUnit.MICROSECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        //注册异步执行事件

        //注册观察者事件
        Observable<String> fs = new CommandHelloWorld("World").observe();
        //注册结果回调事件
        fs.subscribe(new Action1<String>() {
            public void call(String result) {
                //执行结果处理,result 为HelloWorldCommand返回的结果
                //用户对结果做二次处理.
                System.out.println("sub " + result);
            }
        });

        //注册完成的执行生命周期事件
        fs.subscribe(new Observer<String>() {
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                System.out.println("execute onCompleted");
            }

            public void onError(Throwable throwable) {
                // 当产生异常时回调
                System.out.println("onError " + throwable.getMessage());
                throwable.printStackTrace();
            }

            public void onNext(String result) {
                // 获取结果后回调
                System.out.println("onNext: " + result);
            }
        });



    }
}
