package com.kt.test.concurenttest;

/**
 * @User: jufeng
 * @Date: 18-1-17
 * @Time: 下午3:04
 **/
public class Test {

    public void some(){
        long a = 0; //a is local var  so  safe
        a++;
    }


}
