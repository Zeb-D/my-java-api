package com.yd.java.concurrency.singletonCase;

/**
 * 主动初始化
 * @author Yd on  2018-05-12
 * @description
 **/
public class EagerInit {
    private static Object object = new Object();
    private static Object getObject(){
        return object;
    }

}
