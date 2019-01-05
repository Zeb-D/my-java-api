package com.yd.java.concurrency.cancelThread;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 把不可靠的取消把生产者置于阻塞的操作中
 */
public class BrokenProducer extends Thread {
    private volatile boolean cancelled = false;
    private final BlockingQueue<BigInteger> queue;

    BrokenProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void cancel() {
        cancelled = true;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {

        }
    }

    public static void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(8);
        BrokenProducer producer = new BrokenProducer(primes);
        producer.start();
        //当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，当系统执行完这些钩子后，jvm才会关闭。
        Runtime.getRuntime().addShutdownHook(producer);//加上关闭钩子
        try{
            while (!primes.isEmpty()){
               BigInteger p = primes.take();
               System.out.println(p);
            }
        }finally {
            producer.cancel();
        }

    }

}
