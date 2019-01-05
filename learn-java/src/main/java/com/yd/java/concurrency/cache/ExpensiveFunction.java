package com.yd.java.concurrency.cache;

import java.math.BigInteger;

/**
 * @author Yd on  2018-02-09
 * @description
 **/
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //模拟一系列的 计算过程，比如计算器。。。
        return new BigInteger(arg);
    }
}
