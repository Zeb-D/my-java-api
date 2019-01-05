package com.yd.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取中文拼音工具类
 *
 * @author Yd on 2018-06-10
 */
public class PinyinUtil {

    /**
     * 获取中文全拼
     *
     * @param cnStr
     * @return
     */
    public static String getAllCharPingYin(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        if (StringUtils.isNotBlank(cnStr)) {
            char[] charArray = null;
            charArray = cnStr.toCharArray();
            String[] strArray = new String[charArray.length];
            HanyuPinyinOutputFormat pyformat = new HanyuPinyinOutputFormat();
            pyformat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            pyformat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            pyformat.setVCharType(HanyuPinyinVCharType.WITH_V);
            int length = charArray.length;
            try {
                for (int i = 0; i < length; i++) {
                    // 判断是否为汉字字符
                    if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        strArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], pyformat);
                        strBuf.append(strArray[0]);
                    } else {
                        strBuf.append(Character.toString(charArray[i]));
                    }
                }
                return strBuf.toString();
            } catch (BadHanyuPinyinOutputFormatCombination e1) {
                e1.printStackTrace();
            }
            return strBuf.toString();
        } else {
            return strBuf.toString();
        }
    }

    /**
     * 获取中文首字母
     *
     * @param cnStr
     * @return
     */
    public static String getHeadCharPinYin(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        if (StringUtils.isNotBlank(cnStr)) {
            for (int j = 0; j < cnStr.length(); j++) {
                char word = cnStr.charAt(j);
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    strBuf.append(pinyinArray[0].charAt(0));
                } else {
                    strBuf.append(word);
                }
            }
            return strBuf.toString();
        } else {
            return strBuf.toString();
        }
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        if (StringUtils.isNotBlank(cnStr)) {
            byte[] bGBK = cnStr.getBytes();
            for (int i = 0; i < bGBK.length; i++) {
                //if(strBuf.toString().equals("")){
                strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
                //}else{
                //	strBuf.append("-").append(Integer.toHexString(bGBK[i] & 0xff));
                //}
            }
            return strBuf.toString();
        } else {
            return strBuf.toString();
        }
    }

    public static void main(String[] args) {
        String cnStr = "xiao";
        String znStr = "曾小贤";
        System.out.println(getAllCharPingYin(znStr));
        System.out.println(getHeadCharPinYin(znStr));
        System.out.println(getCnASCII(cnStr));
    }
}