package com.common;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Yd on  2018-01-19
 * @Description：
 **/
public class BigdecmalTest {
    @Test
    public void testBigDecimal() {
        BigDecimal decimal = new BigDecimal("123654.12365");
        DecimalFormat format = new DecimalFormat("#0");
        String str = format.format(decimal);
        System.out.println(str);
        Integer a = Integer.parseInt(str);
        System.out.println(a);
    }
}
