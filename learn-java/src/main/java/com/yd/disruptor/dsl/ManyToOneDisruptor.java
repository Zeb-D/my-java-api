package com.yd.disruptor.dsl;

/**
 * Created by Zeb灬D on 2017/11/22
 * Description:
 */

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * <pre>
 *
 * Sequence a series of events from multiple publishers going to one event processor.
 *
 * +----+
 * | P1 |------+
 * +----+      |
 *             v
 * +----+    +-----+
 * | P2 |--->| EP1 |
 * +----+    +-----+
 *
 * Disruptor:
 * ==========
 *             track to prevent wrap
 *             +--------------------+
 *             |                    |
 *             |                    v
 * +----+    +====+    +====+    +-----+
 * | P1 |--->| RB |<---| SB |    | EP1 |
 * +----+    +====+    +====+    +-----+
 *             ^   get    ^         |
 * +----+      |          |         |
 * | P2 |------+          +---------+
 * +----+                   waitFor
 *
 *
 * P1  - Publisher 1
 * P2  - Publisher 2
 * RB  - RingBuffer
 * SB  - SequenceBarrier
 * EP1 - EventProcessor 1
 *
 * </pre>
 */
public class ManyToOneDisruptor {

    private static int RING_BUFFER_SIZE = 1024 * 16;
    private static long ITERATIONS = 1000L * 1000L * 100L;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        // 单个生产者ProducerType.MULTI，消费者的等待策略为YieldingWaitStrategy
        Disruptor<ValueEvent> disruptor =
                new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY,
                        RING_BUFFER_SIZE,
                        DaemonThreadFactory.INSTANCE,
                        ProducerType.MULTI,
                        new YieldingWaitStrategy());

        ValueAdditionEventHandler handler = new ValueAdditionEventHandler();
        // 设置处理者
        disruptor.handleEventsWith(handler);
        // 启动disruptor
        disruptor.start();

        // CountDownLatch是为了保证发布的数据被处理完后，才输出结果
        CountDownLatch latch = new CountDownLatch(1);
        handler.reset(latch, ITERATIONS - 1);

        // 保证2个生产者同时生产数据
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        // 定义生产者，以及生产的数据区间[start, end)
        ValuePublisher publisher1 = new ValuePublisher(cyclicBarrier,
                disruptor.getRingBuffer(),
                0,
                ITERATIONS / 2);
        new Thread(publisher1).start();

        ValuePublisher publisher2 = new ValuePublisher(cyclicBarrier,
                disruptor.getRingBuffer(),
                ITERATIONS / 2,
                ITERATIONS);
        new Thread(publisher2).start();

        // 所有的生产者线程都同时运行
        cyclicBarrier.await();
        // 等待计算完成
        latch.await();
        System.out.println("mutiProcess: " + handler.getValue());
        disruptor.shutdown();
        // 单个线程本地计算
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
        System.out.println("localProcess: " + total);
    }

}