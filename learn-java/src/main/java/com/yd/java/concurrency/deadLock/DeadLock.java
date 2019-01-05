package com.yd.java.concurrency.deadLock;

/**
 * @author Yd on  2018-05-08
 * @description 一个类可能发生死锁，并不意味着每次都会发生死锁，这只是表示有可能。当死锁出现时，往往是在最糟糕的情况----高负载的情况下。
 * 产生死锁的简单代码并且演示如何分析这是一个死锁:
 **/
public class DeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public static void main(String[] args) {
        DeadLock lock = new DeadLock();
        System.identityHashCode(lock);
        new Thread(() -> {
            try {
                lock.leftRight();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.rightLeft();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void leftRight() throws Exception {
        synchronized (left) {
            Thread.sleep(2000);
            synchronized (right) {
                System.out.println("leftRight end!");
            }
        }
    }

    public void rightLeft() throws Exception {
        synchronized (right) {
            Thread.sleep(2000);
            synchronized (left) {
                System.out.println("rightLeft end!");
            }
        }
    }
}
