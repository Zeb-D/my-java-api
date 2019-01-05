package com.yd.common;

/**
 * @author Yd on  2018-01-15
 * @Description：
 **/
public class ExtendsTest {

    public static <T extends Object> void get(T a, T b) {
        System.out.println(a + "" + b);
    }
}
