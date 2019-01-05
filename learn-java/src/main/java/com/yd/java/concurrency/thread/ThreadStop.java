package com.yd.java.concurrency.thread;

/**
 * stop()方法终止线程的运行，他们属于抢占式中断。但它引来了很多问题，早已被JDK弃用。
 * 调用stop()方法则意味着：
 * <p>
 * ①将释放该线程所持的所有锁，而且锁的释放不可控。
 * ②即刻将抛出ThreadDeath异常，不管程序运行到哪里，但它不总是有效，如果存在被终止线程的锁竞争；
 * </p>
 * <p>
 *     解释说明：<p>
 *     第一点将导致数据一致性问题，这个很好理解，一般数据加锁就是为了保护数据的一致性，而线程停止伴随所持锁的释放，很可能导致被保护的数据呈现不一致性，最终导致程序运算出现错误。<p>
 *     第二点比较模糊，它要说明的问题就是可能存在某种情况stop()方法不能及时终止线程，甚至可能终止不了线程。<p>
 * </p>
 *
 * @author Yd on  2018-01-22
 */
public class ThreadStop {
    public static void main(String[] args) {
        Thread mt = new MyThread();
        mt.start();
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mt.stop();
    }

    static class MyThread extends Thread {
        public void run() {
            execute();
        }

        private synchronized void execute() {
            while (true) {
            }
        }
    }
}
