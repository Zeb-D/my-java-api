package com.yd.java.concurrency.threadSignal;

/**
 * 等待通知机制 是 Java 中比较经典的线程通信方式。
 * @author Yd on  2018-05-08
 * @description 两个线程通过对同一对象调用等待 wait() 和通知 notify() 方法来进行通讯。
 * 两个线程交替打印奇偶数：
 * 原理分析：
 *      线程 A 和线程 B 都对同一个对象 TwoThreadWaitNotify.class 获取锁，
 *      A 线程调用了同步对象的 wait() 方法释放了锁并进入 WAITING 状态。
 *      B 线程调用了 notify() 方法，这样 A 线程收到通知之后就可以从 wait() 方法中返回。
 **/
public class TwoThreadWaitNotify {

    private int start = 1;

    private boolean flag = false;

    public static void main(String[] args) {
        TwoThreadWaitNotify twoThread = new TwoThreadWaitNotify();

        Thread t1 = new Thread(new OuNum(twoThread));
        t1.setName("A");

        Thread t2 = new Thread(new JiNum(twoThread));
        t2.setName("B");

        t1.start();
        t2.start();
    }

    /**
     * 偶数线程
     */
    public static class OuNum implements Runnable {
        private TwoThreadWaitNotify number;

        public OuNum(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 10) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+偶数" + number.start);
                        number.start++;

                        number.flag = false;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            System.out.println("偶数线程 等待中");
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    /**
     * 奇数线程
     */
    public static class JiNum implements Runnable {
        private TwoThreadWaitNotify number;
        public JiNum(TwoThreadWaitNotify number) {
            this.number = number;
        }
        @Override
        public void run() {
            while (number.start <= 10) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("奇数线程抢到锁了");
                    if (!number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+奇数" + number.start);
                        number.start++;
                        number.flag = true;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            System.out.println("奇数线程 等待中");
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
