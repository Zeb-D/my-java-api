package com.yd.java.concurrency.cancelThread;

import java.util.concurrent.*;

public class TimeCancelRunable {

    private static ExecutorService executors = Executors.newCachedThreadPool();

    /**
     * 通过future 来取消任务
     *
     * @param r
     * @param timeout
     * @param timeUnit
     * @throws InterruptedException
     */
    public static void timedRun(Runnable r, long timeout, TimeUnit timeUnit) throws InterruptedException {
        Future<?> task = executors.submit(r);
        try {
            task.get(timeout, timeUnit);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //抛出异常
        } catch (TimeoutException e) {
            //任务会被取消
        } finally {
            //如果任务已结束，是无害的
            task.cancel(true);//interrupt if running
        }
    }

}
