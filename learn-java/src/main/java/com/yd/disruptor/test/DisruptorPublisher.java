package com.yd.disruptor.test;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zeb灬D on 2017/10/7
 * Description:
 */
public class DisruptorPublisher implements EventPublisher {

    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
    private Disruptor<TestEvent> disruptor;
    private TestEventHandler handler;
    private RingBuffer<TestEvent> ringbuffer;
    private ExecutorService executor;
    public DisruptorPublisher(int bufferSize, TestHandler handler) {
        this.handler = new TestEventHandler(handler);
        executor = Executors.newSingleThreadExecutor();
        disruptor = new Disruptor<TestEvent>(new TestEventFactory(), bufferSize, executor, ProducerType.SINGLE, YIELDING_WAIT);
    }

    @Override
    public void start() {
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringbuffer = disruptor.getRingBuffer();
    }

    @Override
    public void stop() {
        //System.exit(0);
    }

    @Override
    public void publish(int data) {
        long seq = ringbuffer.next();
        try {
            TestEvent evt = ringbuffer.get(seq);
            evt.setValue(data);
        } finally {
            ringbuffer.publish(seq);
        }
    }

    private class TestEventHandler implements EventHandler<TestEvent> {
        private TestHandler handler;

        public TestEventHandler(TestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onEvent(TestEvent event, long sequence, boolean endOfBatch) throws Exception {
            handler.process(event);
        }

    }

    private class TestEventFactory implements EventFactory<TestEvent> {
        public TestEvent newInstance() {
            return new TestEvent();
        }
    }

}