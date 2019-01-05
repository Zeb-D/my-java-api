package com.yd.javaWeb.io;

import java.io.Serializable;

/**
 * 测试静态变量 是否随对象而变化
 *
 * @author Yd on  2018-05-14
 * @description
 **/
public class Serialize2 implements Serializable {

    public static void main(String[] args) {
        Serialize serialize1 = new Serialize();
        Serialize serialize2 = new Serialize();
        System.out.println(serialize2.id);
        serialize1.id = 10;
        System.out.println(serialize2.id);
    }

}
