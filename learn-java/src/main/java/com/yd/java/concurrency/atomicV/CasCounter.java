package com.yd.java.concurrency.atomicV;

/**
 * 使用CAS实现的非阻塞计数器
 *CAS重要缺点：它强迫调用者处理竞争（重试、回退、放弃），但在获取锁之前可以通过阻塞自动处理竞争
 * @author Yd on  2018-05-12
 * @description
 * 非阻塞算法：
 * 一个线程的失败或挂起不应该影响到其他线程的失败或挂起
 **/
public class CasCounter {
    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }

}
