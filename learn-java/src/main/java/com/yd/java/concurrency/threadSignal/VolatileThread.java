package com.yd.java.concurrency.threadSignal;

import java.util.concurrent.TimeUnit;

/**
 * volatile 共享内存
 * @author Yd on  2018-05-08
 * @description  采用共享内存的方式进行线程通信
 * flag 存放于主内存中，所以主线程和线程 A 都可以看到。
 **/
public class VolatileThread implements Runnable{

    private static volatile boolean flag = true ;

    @Override
    public void run() {
        while (flag){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在运行。。。");
        }
        System.out.println(Thread.currentThread().getName() +"执行完毕");
    }

    private void stopThread(){
        flag = false ;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileThread aVolatile  = new VolatileThread();
        new Thread(aVolatile,"thread A").start();

        System.out.println("main 线程正在运行") ;

        TimeUnit.MILLISECONDS.sleep(100) ;
        aVolatile.stopThread();
    }
}
