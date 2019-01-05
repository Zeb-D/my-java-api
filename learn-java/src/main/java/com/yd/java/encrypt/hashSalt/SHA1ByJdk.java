package com.yd.java.encrypt.hashSalt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息/数字摘要算法
 * 对于长度小于2^64位的消息，SHA1会产生一个160位(40个字符)的消息摘要。当接收到消息的时候，这个消息摘要可以用来验证数据的完整性。在传输的过程中，数据很可能会发生变化，那么这时候就会产生不同的消息摘要。
 *
 * <p>利用JDK提供java.security.MessageDigest类实现SHA1算法
 *
 * @author Yd on 2018/4/20
 * @description 特性：
 * <p>
 * 不可以从消息摘要中复原信息；
 * 两个不同的消息不会产生同样的消息摘要,(但会有1x10 ^ 48分之一的机率出现相同的消息摘要,一般使用时忽略)。
 */
public class SHA1ByJdk {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(getSha1("你若安好，便是晴天"));

    }

    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            //创建SHA1算法消息摘要对象
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            //使用指定的字节数组更新摘要。
            mdTemp.update(str.getBytes("UTF-8"));
            //生成的哈希值的字节数组
            byte[] md = mdTemp.digest();
            //SHA1算法生成信息摘要关键过程
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "0";

    }
}
