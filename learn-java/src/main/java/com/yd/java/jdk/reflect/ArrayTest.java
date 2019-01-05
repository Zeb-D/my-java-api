package com.yd.java.jdk.reflect;

import java.lang.reflect.Array;

/**
 * Java反射机制通过java.lang.reflect.Array这个类来处理数组。
 * 不要把这个类与Java集合套件（Collections suite）中的java.util.Arrays混淆，java.util.Arrays是一个提供了遍历数组，将数组转化为集合等工具方法的类。
 * @author Yd on 2018-07-05
 * @description
 */
public class ArrayTest {
    public static void main(String[] args) throws ClassNotFoundException {
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);

        System.out.println("intArray[0] = " + Array.get(intArray, 0));
        System.out.println("intArray[1] = " + Array.get(intArray, 1));
        System.out.println("intArray[2] = " + Array.get(intArray, 2));

        Class stringArrayClass = String[].class;
        Class stringArrayClass2 = Class.forName("[Ljava.lang.String;");//注意‘[L’的右边是类名，类名的右边是一个‘;’符号。这个的含义是一个指定类型的数组。
        System.out.println("is array: " + stringArrayClass.isArray());

        Class intArrayClass = Class.forName("[I");

        //你不能通过Class.forName()方法获取一个原生数据类型的Class对象。
//        Class intClass1 = Class.forName("I");
//        Class intClass2 = Class.forName("int");

        Class stringArrayComponentType = stringArrayClass.getComponentType();
        System.out.println(stringArrayComponentType);


    }
}
