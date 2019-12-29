package com.yd.disruptor.test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Zeb灬D on 2017/10/7
 * Description:
 */
public class SimpleTracer implements CounterTracer {
    private final long expectedCount;
    private long startTicks;
    private long endTicks;
    private long count = 0;
    private boolean end = false;
    private CountDownLatch latch = new CountDownLatch(1);

    public SimpleTracer(long expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void start() {
        startTicks = System.currentTimeMillis();
        end = false;
    }

    @Override
    public long getMilliTimeSpan() {
        return endTicks - startTicks;
    }

    @Override
    public boolean count() {
        if (end) {
            return end;
        }
        count++;
        end = count >= expectedCount;
        if (end) {
            endTicks = System.currentTimeMillis();
            latch.countDown();
        }
        return end;
    }

    @Override
    public void waitForReached() {
        long s = System.currentTimeMillis();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - s);
    }

    @Override
    public String toString() {
        return expectedCount + "/" + (endTicks - startTicks) + " ==" + expectedCount / (endTicks - startTicks);
    }

    @Override
    public void printResult() {
        System.out.println(this.toString());
    }

}