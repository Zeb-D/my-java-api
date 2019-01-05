package com.yd.java.concurrency.cancelThread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 质素产生，该任务可以随时取消
 */
public class PrimeGenerator implements Runnable{
    private volatile boolean canceled;
    private final List<BigInteger> primes = new ArrayList<BigInteger>();

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!canceled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }

    }

    public synchronized List<BigInteger> getPrimes(){
        return new ArrayList<BigInteger>(primes);
    }

    public void cancel(){
        canceled = true;
    }

    public static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        Thread t = new Thread(primeGenerator);
        try{
            t.start();
            t.sleep(1000);
//            t.interrupt();//中断停止
        }finally {
            primeGenerator.cancel();
        }

        return primeGenerator.getPrimes();
    }

}
