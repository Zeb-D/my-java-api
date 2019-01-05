package com.yd.java.concurrency.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yd on  2018-02-09
 * @description 采用备忘录模式，使用ConcurrentHashMap<A, V> 作为缓存对象
 **/
public class Memorizer2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> computable;

    public Memorizer2(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        //缓存取值，没有则计算后加入缓存，这几步动作并不是原子性操作，存在重复进入 计算后加缓存
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
