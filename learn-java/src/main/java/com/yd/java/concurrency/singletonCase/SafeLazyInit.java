package com.yd.java.concurrency.singletonCase;

/**
 * 线程安全的惰性初始化——懒汉加载
 * @author Yd on  2018-05-12
 * @description 懒汉式是 节省内存，不使用的时候不初始化；
 **/
public class SafeLazyInit {
    private static Object object;

    public synchronized static Object getObject(){
        if (object ==null)
            object = new Object();
        return object;
    }
}
