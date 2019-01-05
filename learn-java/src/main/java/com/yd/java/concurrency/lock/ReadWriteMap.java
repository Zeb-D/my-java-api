package com.yd.java.concurrency.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用读写锁包装Map
 * @author Yd on  2018-05-10
 * @description 避免 读-写 写-写冲突
 * Lock 比内部锁 提供了一些扩展的特性，包括：
 * 处理不可用的锁时更好的灵活性，对队列行为更好的控制，不能完全替代synchronized
 **/
public class ReadWriteMap<K,V> {
    private final Map<K,V> map ;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();
    public ReadWriteMap(Map<K,V> map){
        this.map = map;
    }

    public V put(K key,V value){ //remove putAll clear
        write.lock();
        try{
            return map.put(key,value);
        }finally {
            write.unlock();
        }
    }

    public V get(V value){
        read.lock();
        try{
            return map.get(value);
        }finally {
            read.unlock();
        }
    }

}
