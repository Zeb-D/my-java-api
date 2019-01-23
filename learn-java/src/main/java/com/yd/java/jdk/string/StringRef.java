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

        //研究字符串常量池 与 final 关键字
        String a = "xiaomeng2";//字符串常量池中的 xiaomeng2
        final String b1 = "xiaomeng";// final 修饰的，变量  b 的值在编译时候就已经确定了它的确定值，换句话说就是提前知道了变量 b 的内容到底是个啥，相当于一个编译期常量；
        String b2 = "xiaomeng";//指向常量池中 xiaomeng，但由于 d 不是 final 修饰，也就是说在使用 d 的时候不会提前知道 d 的值是什么
        String c = b1 + 2;//由于 b1 是一个常量，所以在使用 b 的时候直接相当于使用 b 的原始值（xiaomeng）来进行计算，所以 c 生成的也是一个常量
        String e = b2 + 2;//e的话由于使用的是 d 的引用计算，变量d的访问却需要在运行时通过链接来进行，所以这种计算会在堆上生成 xiaomeng2 ,所以最终 e 指向的是堆上的 xiaomeng2
        System.out.println((a == c));
        System.out.println((a == e));
    }
}
