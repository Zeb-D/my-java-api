package com.yd.java.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用lock实现的计数信号量
 * @author Yd on  2018-05-11
 * @description 这不是java.util.concurrent.Semaphore 的真正实现；
 * 不过与java.util.concurrent.locks.ReentrantLock 它们共同基于AQS（AbstractQueuedSynchronizer）抽象队列同步器 实现的
 **/
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    private final Condition permitsAvalible = lock.newCondition();
    private int permits;

    public SemaphoreOnLock(int permits) {
        lock.lock();
        try {
            this.permits = permits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0)
                permitsAvalible.await();
            --permits;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++permits;
            permitsAvalible.signal();
        } finally {
            lock.unlock();
        }
    }

}
