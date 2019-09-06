package com.yd.java.jdk.time;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用单个调度线程来按毫秒更新时间戳，相当于维护一个全局缓存。
 * 其他线程取时间戳时相当于从内存取，不会再造成时钟资源的争用，代价就是牺牲了一些精确度。
 * 注意：要在高并发下使用能看出效果，量小反而效果差
 *
 * @author created by Zeb灬D on 2019-09-06 10:12
 */
public class CurrentTimeMillisClock {
    private volatile long now;

    private CurrentTimeMillisClock() {
        now = System.currentTimeMillis();
        scheduleTick();
    }

    public long now() {
        return now;
    }

    public static CurrentTimeMillisClock getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CurrentTimeMillisClock INSTANCE = new CurrentTimeMillisClock();
    }

    private void scheduleTick() {
        new ScheduledThreadPoolExecutor(1, r -> {
            Thread thread = new Thread(r, "Current Time");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(() -> {
            now = System.currentTimeMillis();
        }, 1, 1, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        long begin = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            CurrentTimeMillisClock.getInstance().now();
        }
        System.out.println("3 ==> time:" + (System.nanoTime() - begin));
    }
}
