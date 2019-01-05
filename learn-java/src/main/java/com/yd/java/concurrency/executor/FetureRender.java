package com.yd.java.concurrency.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yd on  2018-02-12
 * @description 页面渲染
 **/
public class FetureRender {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

}
