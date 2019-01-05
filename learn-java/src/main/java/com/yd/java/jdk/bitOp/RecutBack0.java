package com.yd.java.jdk.bitOp;

/**
 * 为何从10开始到99连续相乘会得到0？
 *
 * @author Yd on 2018-06-22
 */
public class RecutBack0 {

    public static void main(String[] args) {

        int product = 1;

        for (int i = 10; i <= 99; i++) {

            product *= i;
            System.out.println(String.format(" %s * %s =:%s", product / i, i, product));

        }

        System.out.println(product);

    }
}
