package com.yd.java.concurrency.threadSignal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * CountDownLatch 并发工具
 *
 * @author Yd on  2018-05-08
 * @description
 **/
public class ConcurrencyThreadUtil {

    //CountDownLatch 可以实现 join 相同的功能，但是更加的灵活。
    //CountDownLatch 也是基于 AQS(AbstractQueuedSynchronizer) 实现的，更多实现参考 ReentrantLock 实现原理
    //初始化一个 CountDownLatch 时告诉并发的线程，然后在每个线程处理完毕之后调用 countDown() 方法。
//    该方法会将 AQS 内置的一个 state 状态 -1 。
//    最终在主线程调用 await() 方法，它会阻塞直到 state == 0 的时候返回。
    private static void countDownLatch() throws Exception {
        int thread = 3;
        long start = System.currentTimeMillis();
        final CountDownLatch countDown = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread run");
                    try {
                        Thread.sleep(2000);
                        countDown.countDown();
                        System.out.println("thread end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDown.await();
        long stop = System.currentTimeMillis();
        System.out.println("main over total time=" + (stop - start));
    }

    /**
     * CyclicBarrier 中文名叫做屏障或者是栅栏，也可以用于线程间通信。
     * 它可以等待 N 个线程都达到某个状态后继续运行的效果。
     首先初始化线程参与者。
     调用 await() 将会在所有参与者线程都调用之前等待。
     直到所有参与者都调用了 await() 后，所有线程从 await() 返回继续后续逻辑。
     * @throws Exception
     */
    private static void cyclicBarrier() throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3) ;

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread end do something");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread end do something");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread run");
                try {
                    Thread.sleep(5000);
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread end do something");
            }
        }).start();

        System.out.println("main thread");
    }

}
