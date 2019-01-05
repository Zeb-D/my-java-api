package com.yd.java.concurrency.threadSignal;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 awaitTermination() 方法
 * 如果是用线程池来管理线程，可以使用以下方式来让主线程等待线程池中所有任务执行完毕:
 * @author Yd on  2018-05-08
 * @description 使用这个 awaitTermination() 方法的前提需要关闭线程池，如调用了 shutdown() 方法。
 * 调用了 shutdown() 之后线程池会停止接受新任务，并且会平滑的关闭线程池中现有的任务。
 **/
public class ExecutorAwaitTermination {

    private static void executorService() throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MILLISECONDS, queue);
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程还在执行。。。");
        }
        System.out.println("main over");
    }
}
