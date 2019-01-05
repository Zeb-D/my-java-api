package com.yd.java.jdk.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <? extends T> <? super T >
 * <? super T>表示包括T在内的任何T的父类，<? extends T>表示包括T在内的任何T的子类
 *
 * @author Yd on 2018-06-21
 * <p>
 * 请记住PECS原则：生产者（Producer）使用extends，消费者（Consumer）使用super。
 * <p>
 * 生产者使用extends
 * 如果你需要一个列表提供T类型的元素（即你想从列表中读取T类型的元素），你需要把这个列表声明成<? extends T>，比如List<? extends Integer>，因此你不能往该列表中添加任何元素。
 * <p>
 * 消费者使用super
 * 如果需要一个列表使用T类型的元素（即你想把T类型的元素加入到列表中），你需要把这个列表声明成<? super T>，比如List<? super Integer>，因此你不能保证从中读取到的元素的类型。
 * <p>
 * 即是生产者，也是消费者
 * 如果一个列表即要生产，又要消费，你不能使用泛型通配符声明列表，比如List<Integer>。
 * java.util.Collections里的copy方法
 */
public class GenericTest {

    public static void main(String[] args) {
        List<? super Integer> src = new ArrayList<Number>();
        src.add(new Integer(1));

//        List<? extends Number> dest = new ArrayList<Integer>();
//        dest.add(new Integer(2));
//        Collections.copy(src,dest);
    }

    public void testExtends() {
        // Number "extends" Number (in this context)
//        List<? extends Number> foo1 = new ArrayList<? extends Number>();
//
//        // Integer extends Number
//        List<? extends Number> foo2 = new ArrayList<? extends Integer>();
//
//        // Double extends Number
//        List<? extends Number> foo3 = new ArrayList<? extends Double>();

    }

    public void testSupper() {
        // Integer is a "superclass" of Integer (in this context)
        List<? super Integer> foo1 = new ArrayList<Integer>();

        // Number is a superclass of Integer
        List<? super Integer> foo2 = new ArrayList<Number>();

        // Object is a superclass of Integer
        List<? super Integer> foo3 = new ArrayList<Object>();
    }

}
