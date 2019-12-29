package com.yd.disruptor.test;

public interface EventPublisher {
    void publish(int data);

    void start();

    void stop();
}
