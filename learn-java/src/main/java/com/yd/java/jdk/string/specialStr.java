package com.yd.java.jdk.string;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 特殊的字符串
 *
 * @author zouYd created on 2019-01-15
 */
public class specialStr {
    public static final String[] MONTHS = {"㋀", "㋁", "㋂", "㋃", "㋄", "㋅", "㋆", "㋇", "㋈", "㋉", "㋊", "㋋"};
    public static final String[] DAYS = {"㏠", "㏡", "㏢", "㏣", "㏤", "㏥", "㏦", "㏧", "㏨", "㏩", "㏪", "㏫", "㏬", "㏭",
            "㏮", "㏯", "㏰", "㏱", "㏲", "㏳", "㏴", "㏵", "㏶", "㏷", "㏸", "㏹", "㏺", "㏻", "㏼", "㏽", "㏾"};
    public static final String[] HOURS = {"㍘", "㍙", "㍚", "㍛", "㍜", "㍝", "㍞", "㍟", "㍠", "㍡", "㍢", "㍣",
            "㍤", "㍥", "㍦", "㍧", "㍨", "㍩", "㍪", "㍫", "㍬", "㍭", "㍮", "㍯", "㍰"};

    public static void main(String[] args) {
        System.out.println("㋀ length:" + MONTHS[1].length());
        System.out.println("㋀".getBytes(Charset.forName("utf-8")).length);
        System.out.println(Arrays.asList(MONTHS));
        System.out.println(Arrays.asList(DAYS));
        System.out.println(Arrays.asList(HOURS));
    }
}
