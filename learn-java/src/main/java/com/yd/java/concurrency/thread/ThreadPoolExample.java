package com.yd.java.concurrency.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author Yd on  2018-01-22
 * @Description：
 **/
public class ThreadPoolExample {
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue(1024), new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build(), new ThreadPoolExecutor.AbortPolicy());
}
