package com.yd.java.concurrency.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yd on  2018-02-09
 * @description 采用备忘录 模式，使用HashMap+synchronized 来保证 该计算缓存的线程安全
 **/
public class Memorizer1<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> computable;

    public Memorizer1(Computable<A, V> computable) {
        this.computable = computable;
    }


    @Override//使用synchronized 保守的方式来保证线程安全，synchronized的并发效果 有可能比不并发差
    public synchronized V compute(A arg) throws InterruptedException {
        //先读取缓存中的结果，不存在则继续计算及加入缓存中， 该操作有点像 备忘录 模式
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
