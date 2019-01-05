package com.yd.java.concurrency.cache;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BoundedBuffer 生产者-消费者 测试程序
 *
 * @author Yd on  2018-05-10
 * @description
 **/
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger();
    private final AtomicInteger takeSum = new AtomicInteger();
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials, nParis;
    private final BarriedTimer barriedTimer = new BarriedTimer();

    PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<>(capacity);
        this.nTrials = ntrials;
        this.nParis = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1,barriedTimer);
    }

    public static void main(String[] args) {
        new PutTakeTest(10, 10, 100000).test();
        pool.shutdown();
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    void test() {
        try {
            barriedTimer.clear();
            for (int i = 0; i < nParis; i++) {
                pool.execute(() -> {
                    try {
                        int seed = (this.hashCode() ^ (int) System.nanoTime());
                        int sum = 0;
                        for (int j = 0; j < nTrials; j++) {
                            bb.put(seed);
                            sum += seed;
                            seed = xorShift(seed);
                        }
                        putSum.getAndAdd(sum);
                        barrier.await();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                pool.execute(() -> {
                    try {
                        barrier.await();
                        int sum = 0;
                        for (int j = 0; j < nTrials; j++) {
                            sum += bb.take();
                        }
                        takeSum.getAndAdd(sum);
                        barrier.await();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            barrier.await();
            barrier.await();
            System.out.println(putSum.get() == takeSum.get());
            long nsPerItem =barriedTimer.getTime()/(nParis* (long)nTrials);
            System.out.println(nsPerItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void testPoolExpansion() throws InterruptedException {
        int MAX_SIZE = 10;
        TestingThreadFactory threadFactory = new TestingThreadFactory();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);
        for (int i = 0; i < 10 * MAX_SIZE; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (int i = 0; i < 20 && threadFactory.getNumCreated().get() < MAX_SIZE; i++) {
            Thread.sleep(100);
        }
        executor.shutdown();
    }

}

class TestingThreadFactory implements ThreadFactory {
    private final AtomicInteger numCreated = new AtomicInteger();
    private final ThreadFactory factory = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        numCreated.incrementAndGet();
        return factory.newThread(r);
    }

    public AtomicInteger getNumCreated() {
        return this.numCreated;
    }
}

class BarriedTimer implements Runnable {
    private boolean started;
    ;
    private long start, end;

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return end - start;
    }

    @Override
    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
        } else end = t;
    }
}

