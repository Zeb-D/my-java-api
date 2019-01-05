package com.yd.java.jdk.string;

import java.util.Date;

/**
 * 研究String对象 引用问题<p>
 * 原理：<p>编译期间：Class类文件的常量池（javap）、符号引用、字面量
 * <p>运行期：进行类加载后，就涉及到字符串常量池、堆
 * @author Yd on  2018-06-30
 * @description
 **/
public class StringRef {
    public static void main(String[] args) {
        String s1 = "Hollis";
        String s2 = new String("Hollis");
        String s3 = new String("Hollis").intern();

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2.intern() == s3);
        System.out.println(s2==new String("Hollis"));

        //String s1 = "Hollis";和String s3 = new String("Hollis").intern();做的事情是一样的,都是定义一个字符串对象，然后将其字符串字面量保存在常量池中，并把这个字面量的引用返回给定义好的对象引用。
        //String s3 = new String("Hollis").intern();，在未调用intern时候，s3指向的是JVM在堆中创建的那个对象的引用的。但是当执行了intern方法后，s3将指向字符串常量池中的那个字符串常量。
        Date date = new Date(1530526188);
        System.out.println(date);
    }
}
