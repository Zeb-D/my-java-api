package com.yd.java.concurrency;

import com.yd.java.concurrency.task.FileCrawler;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yd on  2018-02-07
 * @description 赛马测试
 **/
public class TestHarness {

    public static void main(String[] args) throws InterruptedException {
        String rootPath = "D:\\ChromeDownload";
        String fileNameLike = "apache";
        Long taskTime = timeTasks(2, new FileCrawler(new File(rootPath), fileNameLike));
        System.out.println("aaaa:" + taskTime);
    }

    //设置开始等待门为1、结束门为当前线程数，run之后结束门 减1，当所有的线程跑完结束门为0；
    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long startTime = System.nanoTime();
        startGate.countDown();//开始
        endGate.await();//只要结束门不为0，这里就继续阻塞
        return System.nanoTime() - startTime;
    }

}
