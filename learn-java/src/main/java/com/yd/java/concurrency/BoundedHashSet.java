package com.yd.java.concurrency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author Yd on  2018-02-08
 * @description
 **/
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound){
        this.set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean isAdd = false;
        try{
            isAdd = set.add(o);
            return isAdd;
        }finally {
            if (!isAdd){
                semaphore.release();
            }
        }
    }

    public boolean remove(T o){
        boolean isRemoved = set.remove(o);
        if (isRemoved){
            semaphore.release();
        }
        return isRemoved;
    }

}
