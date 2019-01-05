package com.yd.java.concurrency.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Yd on  2018-01-22
 * @Description：
 **/
public class CountDownExample {
    public void operate(CountDownLatch countDownLatch){
        try{
            System.out.println("business logic");
        }catch (RuntimeException e){
            // do something
        }finally {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        singleThreadPool.shutdown();

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        },100,100, TimeUnit.HOURS);

        Long randomLong = new Random().nextLong();

        ConcurrentSkipListMap m = new ConcurrentSkipListMap();
        ConcurrentHashMap map = new ConcurrentHashMap();
    }
}
