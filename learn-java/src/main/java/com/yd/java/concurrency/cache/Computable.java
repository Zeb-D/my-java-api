package com.yd.java.concurrency.cache;

/**
 * @author Yd on  2018-02-09
 * @description 计算
 **/
public interface Computable<A,V> {
    /**
     * 输入一个参数，返回一个结果
     * @param arg
     * @return
     * @throws InterruptedException
     */
    V compute(A arg) throws InterruptedException;
}
