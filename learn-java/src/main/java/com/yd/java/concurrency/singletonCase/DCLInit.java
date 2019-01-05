package com.yd.java.concurrency.singletonCase;

/**
 * DCL 双重检查锁
 *
 * @author Yd on  2018-05-12
 * @description
 **/
public class DCLInit {
    private static volatile Object object= null;

    public static Object getInstance() {
        if (object == null) {
            synchronized (DCLInit.class) {
                if (object == null) {
                    object = new Object();
                }
            }
        }

        return object;
    }

}
