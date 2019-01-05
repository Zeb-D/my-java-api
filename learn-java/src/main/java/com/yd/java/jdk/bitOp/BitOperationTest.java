package com.yd.java.jdk.bitOp;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 位运算符
 * 源码 与 补码 之间的关系要清楚
 * @author Yd on  2018-05-16
 * @description &：按位与
 * |：按位或
 * ^: 按位异或
 * <<:左移
 * <<：右移
 * <<<:无符号右移
 * 除 ～ 以 外 ,其余 均 为 二 元 运 算 符 ；操 作 数 只 能 为 整 型 和字 符 型 数 据；
 * Java使用补码来表示二进制数 ,在补码表示中 ,最高位为符号位 ,正数的符号位为0,负数为1。
 * 如 +42的补码 为 00101010。 那么-42的补码11010101 +01 = 11010110
 **/
public class BitOperationTest {

    //按位与：只有两个操作数对应位同为1时，结果为1，其余全为0. （或者只要有一个操作数为0，结果就为0）。
    public static void testBitAnd(){
        int a = 10;// 0000 1010
        int b = 12;// 0000 1100
        int c = 8; // 0000 1000
        System.out.println("位运算：按位与 a&b="+(a&b));
    }

    //按位或:两个操作数对应位同为0时，结果为0，其余全为1
    public static void testBitOr(){
        int a = 10;// 0000 1010
        int b = 12;// 0000 1100
        int c = 14; // 0000 1110
        System.out.println("位运算：按位或 a|b="+(a|b));
    }

    //按位非：按位取反
    public static void testBitNot(){
        int a = 10;// 0000 1010
        int b = -11;// 1111 0101
        System.out.println("位运算：按位或 ~a="+(~a));
    }

    //按位异或:两个操作数对应位不同为1，相同为0
    public static void testBitXoR(){
        int a = 10;// 0000 1010
        int b = 12;// 0000 1100
        int c = 6; // 0000 0110
        System.out.println("位运算：按异或 a^b="+(a^b));
    }

    //左位移：符号位不变，低位补0。 相当于 乘积 2的位数幂
    public static void testLShift(){
        int a = 10;// 0000 1010
        int b = 40;// 0010 1000
        System.out.println("位运算：左移 a<<2="+(a<<2));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE)+Integer.MAX_VALUE);
    }

    //低位溢出，符号位不变，并用符号位补溢出的高位. 相当于 除以 2的位数幂
    public static void testRShift(){
        int a = 10;// 0000 1010
        int b = 2;// 0000 0010
        System.out.println("位运算：右移 a>>2="+(a>>2));
    }

    //无符号右移：低位溢出，高位补0。注意，无符号右移（>>>）中的符号位（最高位）也跟着变，无符号的意思是将符号位当作数字位看待。
    public static void testUnsignedRShift(){
        int a = -1;// 1111 1111
        int b = Integer.MAX_VALUE;// 0111 1111
        System.out.println("位运算：无符号右移 a>>>2="+(a>>>1));

        System.out.println((1 << 31));
        System.out.println(~(1 << 31));
        System.out.println((1 << -1)-1);
        System.out.println(~(1 << -1));
    }

    public static void main(String[] args) {
        testBitAnd();
        testBitOr();
        testBitNot();
        testBitXoR();
        testLShift();
        testRShift();
        testUnsignedRShift();
        int HASH_BITS = 0x7fffffff;
        System.out.println(HASH_BITS);
    }
}
