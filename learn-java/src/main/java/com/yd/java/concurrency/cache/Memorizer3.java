package com.yd.java.concurrency.cache;


import com.yd.java.concurrency.PreLoader;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Yd on  2018-02-09
 * @description 备忘录模式, 使用ConcurrentHashMap<A, Future<V>> 作为缓存，非常好的并发性，
 * 但加入了Future带来了缓存污染，Future的Callable的取消或失败，都会导致未来相同的值 出现第一次的行为
 * 可以通过FutureTask 的子类来设置缓存 过期时间
 **/
public class Memorizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> computable;

    public Memorizer3(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override//if 代码块没有原子操作（检查再运行），但仍然存在：两个线程同时计算同个值，都在缓存没找到，并都开始计算
    public V compute(A arg) throws InterruptedException {
        //缓存取值，没有则计算后加入缓存，这几步动作并不是原子性操作，存在重复进入 计算后加缓存
        Future<V> result = cache.get(arg);
        if (result == null) {//缺少即加入 属于 复合操作
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return computable.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(callable);
            result = ft;
            cache.put(arg, result);
            ft.run();//在这里调用 Callable 中的 computable.compute(arg)  ，如果Future call 失败了，未来相同的值进行 尝试 都可能会表现为第一次的结果
        }
        try {
            return result.get();//返回 最终结果
        } catch (ExecutionException e) {
            throw PreLoader.launderThrowable(e);
        }
    }
}
