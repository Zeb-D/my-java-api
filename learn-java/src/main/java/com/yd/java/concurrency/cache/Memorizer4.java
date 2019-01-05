package com.yd.java.concurrency.cache;


import com.yd.java.concurrency.PreLoader;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Yd on  2018-02-09
 * @description 3的加强版，对缺少即加入的缺陷 引进
 * 是前面的Memorizer 的实现 最终版，
 **/
public class Memorizer4<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> computable;
//    ExecutorService
    public Memorizer4(Computable<A, V> computable) {
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
            result = cache.putIfAbsent(arg, ft);//缺少即加入 ,这里使用到了原子操作
            if (result == null) {
                result = ft;
                ft.run();
            }
        }
        try {
            return result.get();//返回 最终结果
        } catch (CancellationException e) {//FutureTask 执行取消则移除，注：未来尝试可能会成功，将结果放入到缓存
            cache.remove(arg, result);
            throw PreLoader.launderThrowable(e.getCause());//移除后，为什么要异常
        } catch (ExecutionException e) {
            throw PreLoader.launderThrowable(e.getCause());
        }
    }

}
