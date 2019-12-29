package com.yd.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

/**
 * Created by Zeb灬D on 2017/9/29
 * Description:
 */
public class DisruptorStart {
    public static void main(String[] args) throws InterruptedException {
        // 事件工厂
        LongEventFactory factory = new LongEventFactory();

        // 指明RingBuffer的大小，必须为2的幂
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy());

        // 置入处理逻辑
        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        // 获取ringBuffer，用于发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            //Thread.sleep(1000);
        }
    }
}