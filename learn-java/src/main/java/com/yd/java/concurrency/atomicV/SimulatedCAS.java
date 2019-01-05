package com.yd.java.concurrency.atomicV;

/**
 * 模拟CAS操作
 *
 * @author Yd on  2018-05-12
 * @description
 **/
public class SimulatedCAS {
    private int value;

    public int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = expectedValue;
        if (oldValue == expectedValue) value = newValue;
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}
