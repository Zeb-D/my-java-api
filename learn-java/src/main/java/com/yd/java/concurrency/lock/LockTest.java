package com.yd.java.concurrency.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * @author Yd on  2018-05-10
 * @description 在java 5.0内部锁与ReentrantLock 相比，还具有 ： 线程转储能够显示 哪些个调用框架获得了锁，
 * 并能够识别发生了死锁的那些线程；
 * 但是在java 6，提供了一个管理和调试接口，从线程转储中得到ReentrantLock的枷锁信息。这些用于调试消息的可用性对于synchronized 是有优势的，即使它们大部分是即使消息；
 * 总之，ReentrantLock 的非块结构的特性 仍然意味着获取锁不能依赖于特定的栈结构，这一点与内部锁是不同的。
 * synchronized 是内置于JVM的，它能够进行优化，比如对线程限制的锁对象的锁省略，粗化锁来减少内部锁的同步性
 **/
public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        try{
            lock.lock();
        }finally {
            lock.unlock();
        }

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        Condition condition = lock.newCondition();
        condition.await();
        condition.signal();
    }

}
