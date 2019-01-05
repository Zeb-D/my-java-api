package com.yd.java.concurrency.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yd on  2018-05-07
 * @description 定制的线程工程
 **/
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyThread(r, poolName);
    }

}

//自定义线程基类
class MyThread extends Thread {
    public static final String DEFAULT_NAME = MyThread.class.getSimpleName();
    public static final AtomicInteger alive = new AtomicInteger();
    private static final AtomicInteger created = new AtomicInteger();
    private static volatile boolean debugLifecyle = false;

    public MyThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler((t, e) -> System.out.println("uncauht in thread " + t.getName() + "-" + e.getMessage()));
    }

    public void run() {
        boolean debug = debugLifecyle;
        if (debug) System.out.println("created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) System.out.println("exiting " + getName());
        }
    }

}
