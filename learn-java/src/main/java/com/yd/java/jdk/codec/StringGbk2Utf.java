package com.yd.java.jdk.codec;

import java.io.UnsupportedEncodingException;

/**
 * Java 中文字符串编码之GBK转UTF-8
 *
 * @author Yd on  2018-06-13
 * @description
 **/
public class StringGbk2Utf {

    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String name = "永啊";
        String utf = new String(name.getBytes());
        System.out.println(utf);
        String gbk = new String(utf.getBytes("GBK"));
        System.out.println(gbk);

        System.out.println(getUTF8StringFromGBKString(utf));

        String utf1 = new String(gbk.getBytes("UTF-8"));
        System.out.println(utf);
    }
}
