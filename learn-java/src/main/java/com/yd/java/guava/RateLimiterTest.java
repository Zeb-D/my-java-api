package com.yd.java.guava;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流
 * @author Yd on  2018-09-03
 * @description
 **/
public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1);//定义了一个QPS为1的全局限流器
        rateLimiter.tryAcquire();
        boolean rate = rateLimiter.tryAcquire(2);
        System.out.println(rate);
    }
}
