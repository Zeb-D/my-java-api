package com.yd.java.concurrency.definationUtil;

/**
 * 有限缓存使用了拙劣的阻塞 ：轮询+阻塞
 * @author Yd on  2018-05-11
 * @description
 * 选择休眠的时间间隔，是在响应性与cpu 使用率之间作出的权衡；
 * 休眠时间间隔越小，响应性越好，但是cpu的消耗也越高
 * 但是 轮询与休眠 组成一个阻塞操作的尝试都不能令人非常满意：
 *          如果存在某种挂起线程的方法，能够保证当某个条件为真时，线程可以及时苏醒过来，这就是 条件队列
 **/
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private final long WAIT_SECONDS;
    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
        this.WAIT_SECONDS = 1000;
    }


    public void put(V v) throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isFull()){
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(WAIT_SECONDS);
        }
    }

    public V take() throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isEmpty()){
                    return doTake();
                }
            }
            Thread.sleep(WAIT_SECONDS);
            Thread.yield();//
        }
    }

}
