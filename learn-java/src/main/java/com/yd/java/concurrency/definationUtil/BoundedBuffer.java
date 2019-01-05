package com.yd.java.concurrency.definationUtil;

/**
 * 有限缓存使用条件队列 ： 使用wait 与notifyAll
 *
 * @author Yd on  2018-05-11
 * @description Object 中的wait、notify、notifyAll 方法构成了内部条件队列的api
 * 一个对象的内部锁与他的内部条件队列是相关的：
 * 为了能够调用对象X中的任一个条件队列方法，你必须持有对象X的锁。
 * 这是因为“等待基于状态的条件”机制 必须和“维护状态一致性”机制紧密绑定在一起
 **/
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    //条件谓词 ：not-full ！isFull()
    //条件谓词：not-empty !isEmpty()
    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    //比休眠+轮询 好多了，还可以把阻塞操作在预计时间内完成
    public synchronized void put(V v) throws InterruptedException {
        while (isFull())//阻塞 直到not-full
            wait();//会释放锁，并阻塞当前线程及等待直到特定时间的超时后，线程被中断或者被通知唤醒
        //线程被唤醒后，wait会在返回运行前重新请求锁。它像任何其他尝试进入synchronized块 一样去争夺锁
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) notifyAll();//依据条件通知
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();//notifyAll 比notify 效率好低，会唤醒每一个进程
        return v;
    }

}
