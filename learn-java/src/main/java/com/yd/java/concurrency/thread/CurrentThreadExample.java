package com.yd.java.concurrency.thread;

/**
 * @author Yd on 2019-01-07
 * @description
 */
public class CurrentThreadExample {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println(t.getName());
    }

}
