package com.kt.test.concurenttest;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @User: jufeng
 * @Date: 18-1-17
 * @Time: 下午4:42
 **/
public class ThreadLocalTest {


    //LinkedTransferQueue

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal =
                new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set( (int) (Math.random() * 100D) );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(threadLocal.get());
        }
    }


    static class A{
        private ThreadLocal<Integer> threadLocal =
                new ThreadLocal<>();

        public void set(){
            threadLocal.set( 10 );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(threadLocal.get()+10);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate*/


        MyRunnable sharedRunnableInstance = new MyRunnable();

        A a =new A();
        Thread thread1 = new Thread(() -> a.set());
        Thread thread2 = new Thread(() -> a.set());

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

}
