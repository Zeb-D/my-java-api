package com.yd.java.concurrency.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Yd on  2018-05-08
 * @description 扩展线程池以提供日志和统计功能
 **/
public class TimingThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long taskTime = System.nanoTime() - startTime.get();
        numTasks.incrementAndGet();
        totalTime.addAndGet(taskTime);
        System.out.println(String.format("Thread %s: end %s,time=%dns", t, r, taskTime));
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        System.out.println(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
        super.terminated();
    }
}
