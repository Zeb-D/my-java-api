package com.yd.java.jdk.time;

import java.util.concurrent.CountDownLatch;

/**
 * System.currentTimeMillis()
 * 测试高并发下的性能
 *
 * @author created by Zeb灬D on 2019-09-06 09:47
 */
public class CurrentTimeTest {
    public static final int COUNT = 1000;

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            System.currentTimeMillis();
        }
        System.out.println("1 ==> time:" + (System.nanoTime() - startTime));

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; i++) {
            new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            }).start();
        }
        startTime = System.nanoTime();
        start.countDown();
        end.await();
        System.out.println("2 ==> time:" + (System.nanoTime() - startTime));
    }

    //为什么会这样？
    public static void desc() {
        //来到HotSpot源码的hotspot/src/os/linux/vm/os_linux.cpp文件中，有一个javaTimeMillis()方法，这就是System.currentTimeMillis()的native实现。
        //挖源码就到此为止，因为已经有国外大佬深入到了汇编的级别来探究，详情可以参见《The Slow currentTimeMillis()》这篇文章。简单来讲就是：
        //调用gettimeofday()需要从用户态切换到内核态；
        //gettimeofday()的表现受Linux系统的计时器（时钟源）影响，在HPET计时器下性能尤其差；
        //系统只有一个全局时钟源，高并发或频繁访问会造成严重的争用。
        //HPET计时器性能较差的原因是会将所有对时间戳的请求串行执行。TSC计时器性能较好，因为有专用的寄存器来保存时间戳。缺点是可能不稳定，因为它是纯硬件的计时器，频率可变（与处理器的CLK信号有关）。
        //关于HPET和TSC的细节可以参见https://en.wikipedia.org/wiki/HighPrecisionEventTimer与https://en.wikipedia.org/wiki/TimeStamp_Counter。
    }
}
