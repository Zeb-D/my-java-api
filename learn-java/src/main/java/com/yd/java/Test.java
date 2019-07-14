package com.yd.java;

/**
 * @author created by zouyd on 2019-05-28 21:00
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        String msg = "{aqwe}";
        int startIndex = msg.indexOf("{");
        int endIndex = msg.indexOf("}");
        if (startIndex >= 0 && endIndex > 0 && endIndex > startIndex) {
            String errorEntityJSON = msg.substring(startIndex, endIndex + 1);
            System.out.println(errorEntityJSON);
        }
    }
}
