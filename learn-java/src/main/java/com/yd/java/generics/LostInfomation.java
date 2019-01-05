package com.yd.java.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yd on  2018-01-19
 * @Description：
 **/
public class LostInfomation {

    class A{}
    class B{}
    class C<T>{}

    public static void main(String[] args) {
        List<A> list = new ArrayList<A>();
        System.out.println(Arrays.asList(list.getClass().getTypeParameters()));
    }
}
