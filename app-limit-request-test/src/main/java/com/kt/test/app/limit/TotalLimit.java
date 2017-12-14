package com.kt.test.app.limit;

import java.util.concurrent.atomic.AtomicLong;

public class TotalLimit {

    private AtomicLong  atomic = new AtomicLong(0);
    private long limit;

    public TotalLimit(long limit) {
        this.limit = limit;
    }

    //业务处理之钱call，可惜Java 没有callback
    public boolean incoming() {
        if (atomic.incrementAndGet()>limit){
            return false;
        }
        return true;
    }


    //业务处理完成之后，链接数减1
    public void destroy(){
        atomic.decrementAndGet();
    }
}
