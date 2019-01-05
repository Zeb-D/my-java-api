package com.yd.java.concurrency.lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yd on  2018-05-12
 * @description
 **/
public class AQSTest {
    ReentrantLock lock = new ReentrantLock();
    Semaphore semaphore = new Semaphore(1);
    CountDownLatch downLatch = new CountDownLatch(1);
    //使用AQS类型的同步状态来持有任务的状态-运行、完成、取消，也维护；一些额外的状态变量
    //来持有计算的结果或者抛出的异常
    FutureTask<String> futureTask = new FutureTask<String>(()->{return  "a";});
    CyclicBarrier barrier = new CyclicBarrier(1);

    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
}
