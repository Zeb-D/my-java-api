package com.yd.java.concurrency.thread;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Yd on  2018-01-22
 * @Description：避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed 导致的性能下降。
 **/
public class RandomInThread extends Thread {
    private Random random = ThreadLocalRandom.current();

    @Override
    public void run() {
        long t = random.nextLong();
    }
}
