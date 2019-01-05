package com.yd.java.concurrency.cache;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 利用Semaphore 实现有线缓存
 *
 * @author Yd on  2018-05-10
 * @description Semaphore 中 acquire 使得信号量-1 ，release +1 ，每次操作时
 **/
public class BoundedBuffer<E> {
    private final Semaphore availableItems, availableSpaces;
    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        insert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = extract();
        availableSpaces.release();
        return item;
    }

    private synchronized void insert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : 1;
    }

    private synchronized E extract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : 1;
        return x;
    }

    public static void main(String[] args) {
        //测试阻塞与响应中断
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread(()->{
            try {
                int unused = bb.take();
                System.out.println(" has error !!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        try {
            taker.start();
            Thread.sleep(1000);
            taker.interrupt();
            taker.join(1000);
            System.out.println(taker.isAlive());
        } catch (InterruptedException e) {
            System.out.println(" has error !!!");
        }

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{});
    }

}
