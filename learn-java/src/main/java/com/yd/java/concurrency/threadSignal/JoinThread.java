package com.yd.java.concurrency.threadSignal;

/**
 * join() 方法
 * @author Yd on  2018-05-08
 * @description 在 t1.join() 时会一直阻塞到 t1 执行完毕，所以最终主线程会等待 t1 和 t2 线程执行完毕。
 * 其实从源码可以看出，join() 也是利用的等待通知机制：
 * while (isAlive()) {
        wait(0);
    }
 **/
public class JoinThread {

    private static void join() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;

        t1.start();
        t2.start();
        //等待线程1终止
        t1.join();
        //等待线程2终止
        t2.join();
        System.out.println("main over");
    }
}
