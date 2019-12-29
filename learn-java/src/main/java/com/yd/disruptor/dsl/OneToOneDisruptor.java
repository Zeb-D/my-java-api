package com.yd.disruptor.dsl;

/**
 * Created by Zeb灬D on 2017/11/22
 * Description:
 */

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.CountDownLatch;

/**
 * <pre>
 * UniCast a series of items between 1 publisher and 1 event processor.
 *
 * +----+    +-----+
 * | P1 |--->| EP1 |
 * +----+    +-----+
 *
 * Disruptor:
 * ==========
 *              track to prevent wrap
 *              +------------------+
 *              |                  |
 *              |                  v
 * +----+    +====+    +====+   +-----+
 * | P1 |--->| RB |<---| SB |   | EP1 |
 * +----+    +====+    +====+   +-----+
 *      claim      get    ^        |
 *                        |        |
 *                        +--------+
 *                          waitFor
 *
 * P1  - Publisher 1
 * RB  - RingBuffer
 * SB  - SequenceBarrier
 * EP1 - EventProcessor 1
 *
 * </pre>
 */
public class OneToOneDisruptor {

    private static int RING_BUFFER_SIZE = 1024 * 16;
    private static long ITERATIONS = 1000L * 1000L * 100L;

    public static void main(String[] args) throws InterruptedException {
        // 单个生产者ProducerType.SINGLE，消费者的等待策略为YieldingWaitStrategy
        Disruptor<ValueEvent> disruptor =
                new Disruptor<>(ValueEvent.EVENT_FACTORY,
                        RING_BUFFER_SIZE,
                        DaemonThreadFactory.INSTANCE,
                        ProducerType.SINGLE,
                        new YieldingWaitStrategy());

        ValueAdditionEventHandler handler = new ValueAdditionEventHandler();
        // 设置处理者
        disruptor.handleEventsWith(handler);
        // 启动disruptor
        disruptor.start();

        // CountDownLatch是为了保证发布的数据被处理完后，才输出结果
        CountDownLatch latch = new CountDownLatch(1);
        long expectedCount = ITERATIONS - 1;
        handler.reset(latch, expectedCount);

        // 生产者生产消息，暂时不用translator
        for (int i = 0; i < ITERATIONS; i++) {
            // 生产者设置数据并发布
            long next = disruptor.getRingBuffer().next();
            disruptor.getRingBuffer().get(next).setValue(i);
            disruptor.getRingBuffer().publish(next);
        }

        // 闭锁，等所有的发布的数据被处理完成后，向下执行
        latch.await();
        System.out.println("mutiProcess: " + handler.getValue());
        disruptor.shutdown();
        // 单个线程本地计算结果
        locoalCaculate();
    }

    /**
     * 单个线程本地计算
     */
    private static void locoalCaculate() {
        long total = 0l;
        for (int i = 0; i < ITERATIONS; i++) {
            total += i;
        }
        System.out.println("local: " + total);
    }

}