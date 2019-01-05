package com.yd.java.jdk.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Yd on  2018-05-15
 * @description
 **/
public class MapTest {
    Map map = new HashMap();

    LinkedHashMap linkedHashMap = new LinkedHashMap();

    HashSet hashSet = new HashSet();

    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();

    public static void main(String[] args) {
        int sum = 0;
        int total = 0;
//        abc:
//        for (int i = 0; i < 100; i++) {
//            sum += i;
//            total++;
//            if (sum < 100) continue abc;
//            else continue ;
//        }
        System.out.println(sum + "->" + total);

        int a = 11, b = 1;
        System.out.println(a ^ b);

        boolean aa = true, bb = false;
        System.out.println(aa && bb);
    }

}
