package com.yd.java.java8;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * @author Yd on  2018-04-27
 * @description java8 重要函数接口java.util.function
 *              T Predict<T> bool 判断
 *              T Consumer<T> void 输出
 *              T Function<T,R> R 获取函数
 *              None Supplier<T> T 工厂方法
 *              T UnaryOperator<T> T 逻辑非
 *              (T,T) BinaryOperator<T> T 两个数的操作
 **/
public class HelloLambda {
    public static void main(String[] args) {

        Runnable noArg = () -> System.out.println("Hello Lambda!");
        new Thread(noArg).start();

        new Thread(() -> System.out.println("Hello !")).start();
        Runnable runnable = () -> {
            System.out.println("hello1");
            System.out.println("hello2");
        };
        BinaryOperator<Long> add = (x, y) -> x + y;
        BinaryOperator<Long> addExp = (Long x, Long y) -> x + y;
        Optional.ofNullable(add);
        Long result = add.apply(1L,3L);
        Predicate<Integer> atLeast5 = x -> x > 5;
        System.out.println(atLeast5.test(6));
    }
}
