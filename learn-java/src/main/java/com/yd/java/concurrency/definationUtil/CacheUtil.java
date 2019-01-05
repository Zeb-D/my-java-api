package com.yd.java.concurrency.definationUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 状态依赖的可阻塞行为的结构
 * @author Yd on  2018-05-11
 * @description 生产者-消费者的设计经常会使用ArrayBlockingQueue 这种有限缓存。
 * 一个有限缓存提供的put 和take操作，每一个都有先验条件：
 * 如果依赖于状态的操作在处理先验条件时失败，可以抛出异常，或者返回错误状态，也可以保持阻塞直到对象转入正确的状态
 **/
public class CacheUtil {
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(11);

}
