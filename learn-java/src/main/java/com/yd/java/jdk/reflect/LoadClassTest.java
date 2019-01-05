package com.yd.java.jdk.reflect;

/**
 * 类加载
 * @author Yd on 2018-07-05
 * @description
 */
public class LoadClassTest {
    public static void main(String[] args) {
        ClassLoader classLoader = LoadClassTest.class.getClassLoader();

        try {
            Class aClass = classLoader.loadClass("com.yd.java.jdk.reflect.MyClass");
            System.out.println("aClass.getName() = " + aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
