package com.common;

import com.yd.common.EnumSummry;
import com.yd.common.ExtendsTest;
import org.junit.Test;

/**
 * @author Yd on  2018-01-15
 * @Description：
 **/
public class EnumTest {

    @Test
    public void testEnum() {

        System.out.println(EnumSummry.LogOperType.DELETE.getValue());
        for (EnumSummry.LogOperType operType : EnumSummry.LogOperType.values()) {
            System.out.println(operType + ":" + operType.getValue().getClass());
        }
    }

    @Test
    public void testExtend() {

        ExtendsTest.get("a", "b");
    }
}
