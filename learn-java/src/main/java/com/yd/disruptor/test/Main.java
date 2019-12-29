package com.yd.disruptor.test;

/**
 * Created by Zeb灬D on 2017/10/7
 * Description:http://www.cnblogs.com/haiq/p/4112689.html
 */
public class Main {
    public static void main(String[] args) {
        int DATA_COUNT = 1024 * 1024 * 10;
        CounterTracer tracer = new SimpleTracer(DATA_COUNT);//计数跟踪到达指定的数值；
        TestHandler handler = new TestHandler(tracer);//Consumer 的事件处理；

//		EventPublisher publisher = publisherFactory.newInstance(new PublisherCreationArgs(DATA_COUNT, handler));//通过工厂对象创建不同的 Producer 的实现；
        EventPublisher publisher = new DisruptorPublisher(1024 * 1024, handler);
//		EventPublisher publisher = new DirectingPublisher(handler);
//		EventPublisher publisher = new BlockingQueuePublisher(DATA_COUNT,handler);

        publisher.start();
        tracer.start();

        //发布事件；
        for (int i = 0; i < DATA_COUNT; i++) {
            publisher.publish(i);
        }
        System.out.println("发布完成!");

        //等待事件处理完成；
        tracer.waitForReached();
        publisher.stop();
        //输出结果；
        tracer.printResult();
    }

}