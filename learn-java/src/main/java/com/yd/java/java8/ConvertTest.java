package com.yd.java.java8;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 反转相关操作
 *
 * @author created by Zeb灬D on 2020-05-23 12:06
 */
public class ConvertTest {
    public static void main(String[] args) {
        Map<String, Map<String, Long>> map = new HashMap<>(3);
        //如何将value -> key 互转
        Map<String, Long> t1 = new HashMap<>(4);
        t1.put("a", toLong(null));
        t1.put("b", 1L);
        map.put("20200103", t1);
        Map<String, Long> t2 = new HashMap<>(4);
        t2.put("b", 1L);
        t2.put("c", 1L);
        map.put("20200102", t2);
        Map<String, Long> t3 = new HashMap<>(4);
        t3.put("a", 1L);
        t3.put("d", 1L);
        t3.put("e", toLong(null));
        map.put("20200101", t3);
        Map<String, String> result = new HashMap<>(4);
        map.entrySet().stream().filter(Objects::nonNull).forEach(
                it -> {
                    String dt = it.getKey();
                    it.getValue().keySet().stream().forEach(
                            cid -> result.compute(cid, (k, v) -> StringUtils.isEmpty(v) ? dt : v.compareTo(dt) < 0 ? v : dt)
                    );
                }
        );

        Map<String, Map<String, Long>> sumMap = new HashMap<>(4);
        map.entrySet().stream().filter(Objects::nonNull).forEach(
                it -> {
                    String dt = it.getKey();
                    String dt1 = dt.substring(0, 6);
                    if (sumMap.containsKey(dt1)) {
                        it.getValue().entrySet().stream().forEach(
                                iit -> sumMap.get(dt1).compute(iit.getKey(), (k, v) -> v== null ?toLong(iit.getValue()): toLong(v)+ toLong(iit.getValue()))
                        );
                    } else {
                        sumMap.put(dt1, new HashMap<>(it.getValue()));
                    }

                }
        );
        System.out.println(result);
        System.out.println(sumMap);
    }

    public static long toLong(Number num) {
        if (num == null) {
            return 0;
        }
        return num.longValue();
    }
}
