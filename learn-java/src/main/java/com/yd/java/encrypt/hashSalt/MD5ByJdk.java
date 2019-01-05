package com.yd.java.encrypt.hashSalt;

import java.security.MessageDigest;

/**
 * 常见消息/数字摘要算法
 * MD5的作用是让大容量信息在用数字签名软件签署私人密钥前被"压缩"成一种保密的格式 （也就是把一个任意长度的字节串变换成一定长的十六进制数字串）。
 * <p>
 * 利用JDK提供java.security.MessageDigest类实现MD5算法
 *
 * @author Yd on 2018/4/20
 * @description 特点：
 * <p>
 * 压缩性： 任意长度的数据，算出的MD5值长度都是固定的。
 * 容易计算： 从原数据计算出MD5值很容易。
 * 抗修改性： 对原数据进行任何改动，哪怕只修改1个字节，所得到的MD5值都有很大区别。
 * 强抗碰撞： 已知原数据和其MD5值，想找到一个具有相同MD5值的数据（即伪造数据）是非常困难的。
 */
public class MD5ByJdk {
    // test
    public static void main(String[] args) {
        System.out.println(getMD5Code("你若安好，便是晴天"));
    }

    private MD5ByJdk() {
    }

    // md5加密
    public static String getMD5Code(String message) {
        String md5Str = "";
        try {
            //创建MD5算法消息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");//返回实现指定摘要算法的MessageDigest对象
            //生成的哈希值的字节数组
            byte[] md5Bytes = md.digest(message.getBytes());//使用指定的字节数组对摘要执行最终更新，然后完成摘要计算。
            md5Str = bytes2Hex(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    // 2进制转16进制
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        int temp;
        try {
            for (int i = 0; i < bytes.length; i++) {
                temp = bytes[i];
                if (temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(temp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
