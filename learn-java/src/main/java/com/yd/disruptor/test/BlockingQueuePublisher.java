package com.yd.disruptor.test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Zeb灬D on 2017/10/7
 * Description:
 */
public class BlockingQueuePublisher implements EventPublisher {
    private ArrayBlockingQueue<TestEvent> queue;
    private TestHandler handler;

    public BlockingQueuePublisher(int maxEventSize, TestHandler handler) {
        this.queue = new ArrayBlockingQueue<TestEvent>(maxEventSize);
        this.handler = handler;
    }

    @Override
    public void publish(int data) {
        TestEvent event = new TestEvent();
        event.setValue(data);
        try {
            queue.put(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handle() {
        try {
            TestEvent event;
            while (true) {
                event = queue.take();
                if (event != null && handler.process(event)) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread thread = new Thread(() -> {
            handle();
        });
        thread.start();
    }

    @Override
    public void stop() {

    }
}