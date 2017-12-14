package com.kt.test.app.limit;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/***
 * limit a time for one interface
 */
public class TimerLimit {
    private LoadingCache<Long,AtomicLong> counter=null;
    private long limit;
    /**
     *
     * @param timeKey
     */
    public TimerLimit(long timeKey,long limit){
       this.limit = limit;
       this.counter = CacheBuilder.newBuilder()
                .expireAfterWrite(timeKey+1, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, AtomicLong>() {
                    @Override
                    public AtomicLong load(Long aLong) throws Exception {
                        return new AtomicLong(0);
                    }
                });
    }

    /**
     * call this method for  per request
     * @return
     */
    public boolean incoming(){
        long currentSecond = System.currentTimeMillis()/1000;
        try {
            if (counter.get(currentSecond).incrementAndGet()>limit){
                return false;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return true;
    }




}
