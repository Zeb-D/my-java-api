package com.yd.java.concurrency.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Hashtable;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yd on  2018-05-07
 * @description 线程池的长度，需要理解计算环境、资源预算、和任务的自身特点（如计算、io、还是混合操作）
 * cpu 周期并不是唯一你可以使用线程池管理的资源，其他可以约束资源池大小的资源包括：内存、文件句柄和数据库链接等
 **/
public class FixThreadPool {


    //core-pool-size 、max-pool-size、keep-alive-time管理着线程的创建与销毁
    public void createThreadPool() {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()//  new ThreadPoolExecutor.CallerRunsPolicy()  new ThreadPoolExecutor.DiscardPolicy()  new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(5);
        Executors.newFixedThreadPool(10);
        Executors.newSingleThreadExecutor();

        if (cachedThreadPool instanceof ThreadPoolExecutor){
            ((ThreadPoolExecutor) cachedThreadPool).setCorePoolSize(10);
        }

        ReentrantLock lock =new ReentrantLock();
        lock.lock();

        Hashtable hashtable = new Hashtable();
        hashtable.size();

        Semaphore semaphore = new Semaphore(2);
    }
}
