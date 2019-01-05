package com.yd.java.concurrency.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @author Yd on  2018-05-07
 * @description 使用Semaphore来遏制任务的提交
 **/
public class BoundedExecutor {
    private final Executor executor;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, Semaphore semaphore) {
        this.executor = executor;
        this.semaphore = semaphore;
    }

    public void submitTask(final  Runnable commad) throws InterruptedException {
        semaphore.acquire();
        try{
            executor.execute(()->{
                try{
                    commad.run();
                }finally {
                    semaphore.release();
                }
            });
        }catch (RejectedExecutionException e){
            semaphore.release();
        }
    }

}
