package com.yd.java.java8;

import java.util.function.Consumer;

/**
 * 在Lambda表达式实现中，通过invokedynamic指令调用引导方法生成调用点，
 * 在此过程中，会通过ASM动态生成字节码，
 * 而后利用Unsafe的defineAnonymousClass方法定义实现相应的函数式接口的匿名类，
 * 然后再实例化此匿名类，并返回与此匿名类中函数式方法的方法句柄关联的调用点；
 * 而后可以通过此调用点实现调用相应Lambda表达式定义逻辑的功能。
 *
 * @author created by Zeb灬D on 2020-02-17 11:48
 */
public class JavapTest {
    public static void main(String[] args) {
        Consumer<String> consumer = s -> System.out.println("s");
        consumer.accept("Lambda");
    }
}
