package com.yd.java.concurrency.cancelThread;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p=p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            /* 允许线程退出 */
        }
    }

    public void cancel(){
        interrupt();
    }

}
