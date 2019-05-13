package com.yd.java.concurrency.atomicV;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.DoubleBinaryOperator;

/**
 * 1）LongAdder通过base和cells数组来存储值；
 * <p>
 * （2）不同的线程会hash到不同的cell上去更新，减少了竞争；
 * <p>
 * （3）LongAdder的性能非常高，最终会达到一种无竞争的状态；
 * <p>
 * 注意：
 * 在longAccumulate()方法中有个条件是n >= NCPU就不会走到扩容逻辑了，而n是2的倍数，
 * 那是不是代表cells数组最大只能达到大于等于NCPU的最小2次方？在longAccumulate()方法中有个条件是n >= NCPU就不会走到扩容逻辑了，而n是2的倍数，那是不是代表cells数组最大只能达到大于等于NCPU的最小2次方？
 * 答案：
 * 因为同一个CPU核心同时只会运行一个线程，而更新失败了说明有两个不同的核心更新了同一个Cell，这时会重新设置更新失败的那个线程的probe值，
 * 这样下一次它所在的Cell很大概率会发生改变，如果运行的时间足够长，最终会出现同一个核心的所有线程都会hash到同一个Cell（大概率，但不一定全在一个Cell上）上去更新，
 * 所以，这里cells数组中长度并不需要太长，达到CPU核心数足够了。
 *
 * </p>
 *
 * @author created by zouyd on 2019-05-13 10:24
 */
public class LongAdderTest {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        DoubleAdder doubleAdder = new DoubleAdder();

        DoubleAccumulator doubleAccumulator = new DoubleAccumulator((double left, double right) -> {
            return left + right;
        }, 11);


        //当只有一个线程的时候，AtomicLong反而性能更高，随着线程越来越多，AtomicLong的性能急剧下降，而LongAdder的性能影响很小。
        testAtomicLongVSLongAdder(1, 10000000);
        testAtomicLongVSLongAdder(10, 10000000);
        testAtomicLongVSLongAdder(20, 10000000);
        testAtomicLongVSLongAdder(40, 10000000);
        testAtomicLongVSLongAdder(80, 10000000);
    }


    static void testAtomicLongVSLongAdder(final int threadCount, final int times) {
        try {
            System.out.println("threadCount：" + threadCount + ", times：" + times);
            long start = System.currentTimeMillis();
            testLongAdder(threadCount, times);
            System.out.println("LongAdder elapse：" + (System.currentTimeMillis() - start) + "ms");

            long start2 = System.currentTimeMillis();
            testAtomicLong(threadCount, times);
            System.out.println("AtomicLong elapse：" + (System.currentTimeMillis() - start2) + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    atomicLong.incrementAndGet();
                }
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }
    }

    static void testLongAdder(final int threadCount, final int times) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    longAdder.add(1);
                }
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }
    }
}
