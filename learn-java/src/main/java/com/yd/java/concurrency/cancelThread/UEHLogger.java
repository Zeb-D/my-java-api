package com.yd.java.concurrency.cancelThread;

/**
 * @author Yd on  2018-05-02
 * @description
 **/
public class UEHLogger implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Thread terminated with exception: "+t.getName()+e.getMessage());
    }
}
