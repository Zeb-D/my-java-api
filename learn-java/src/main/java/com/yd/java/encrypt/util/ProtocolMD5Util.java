//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yd.java.encrypt.util;

import java.security.MessageDigest;
import java.text.ParseException;

public class ProtocolMD5Util {
    public ProtocolMD5Util() {
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
            return s;
        } catch (Exception var9) {
            throw new IllegalStateException(var9);
        }
    }

    public static String get16BitMD5(String inputStr) {
        String md5 = getMD5(inputStr);
        return md5.substring(8, 24);
    }

    public static String getMD5(String inputStr) {
        return getMD5(inputStr.getBytes());
    }

    public static void main(String[] args) throws ParseException {
        String abc = "1112221212";
        String ccc = getMD5(abc);
        System.out.println(ccc);
    }
}
