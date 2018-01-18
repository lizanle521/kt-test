package com.kt.test.concurenttest;

import java.util.concurrent.*;

/**
 * @User: jufeng
 * @Date: 18-1-18
 * @Time: 上午10:16
 **/
public class PoolTest {



    ThreadPoolExecutor poolExecutor =
            new ThreadPoolExecutor(10, 10, 50, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(2000),
                    r -> {
                        Thread thread = new Thread(r);
                        thread.setName("cutom");
                        return null;
                    }
                    , (r, executor) -> {

                    });


    public void monitor(){
        poolExecutor.getTaskCount();
        poolExecutor.getCompletedTaskCount();
        poolExecutor.getLargestPoolSize();//线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
        poolExecutor.getPoolSize();
        poolExecutor.getActiveCount();
    }
}
