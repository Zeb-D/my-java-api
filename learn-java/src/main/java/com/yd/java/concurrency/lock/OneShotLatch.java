package com.yd.java.concurrency.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 一个简单的闭锁
 * 二元闭锁使用AQS
 *
 * @author Yd on  2018-05-12
 * @description
 **/
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal(){
        sync.releaseShared(0);
    }
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            //如果闭锁打开则超过（state == 1），否则失败
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(1);//闭锁现在已经打开
            return true;//现在。其他线程可以获得闭锁
        }
    }
}
