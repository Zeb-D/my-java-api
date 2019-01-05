package com.yd.java.jdk.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 最近最少使用
 * <p>
 * 实现原理：LRU是通过双向链表来实现的。
 * 当某个位置的数据被命中，通过调整该数据的位置，将其移动至尾部。
 * 新插入的元素也是直接放入尾部(尾插法)。
 * 这样一来，最近被命中的元素就向尾部移动，那么链表的头部就是最近最少使用的元素所在的位置。
 * 当前实现：
 * HashMap的afterNodeAccess()、afterNodeInsertion()、afterNodeRemoval()方法都是空实现，留着LinkedHashMap去重写。
 * </p>
 * 方案一：使用LinkedHashMap
 *
 * @author Yd on  2018-09-04
 * @description
 **/
public class LRUCacheTest {
    //基于LinkedHashMap 实现LRUCache
    public static void LinkedHashMapCache() {
        int size = 5;

        /**
         * false, 基于插入排序
         * true, 基于访问排序
         */
        Map<String, String> map = new LinkedHashMap<String, String>(size, .75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                boolean tooBig = size() > size;

                if (tooBig) {
                    System.out.println("最近最少使用的key=" + eldest.getKey());
                }
                return tooBig;
            }
        };

        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        System.out.println(map.toString());

        map.put("6", "6");
        map.get("2");
        map.put("7", "7");
        map.get("4");

        System.out.println(map.toString());
    }

    public static void main(String[] args) {
        LinkedHashMapCache();
    }

}
