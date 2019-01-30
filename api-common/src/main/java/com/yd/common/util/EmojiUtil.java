package com.yd.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情工具类
 * @author zouYd created on 2019-01-29
 */
public class EmojiUtil {
    //表情正则
    public static Pattern EMOJI_PATTERN = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udc00-\ud83e\udfff]|[︀-\ufeff]|[ -⟿]|[⬀-\u2bff]|[　-〿]|[㈀-\u32ff]", 66);

    /**
     * 转义标签
     * 将表情 转移成 ##16位二进制##
     *
     * @param content
     * @return
     */
    public static String convertEmoji(String content) {
        if (StringUtils.isBlank(content)) {
            return "";
        } else {
            String temp = new String(content);

            String emo;
            StringBuffer sb;
            for (Matcher m = EMOJI_PATTERN.matcher(temp); m.find(); content = content.replace(emo, sb.toString())) {
                emo = m.group();
                sb = new StringBuffer();

                for (int i = 0; i < emo.length(); ++i) {
                    char c = emo.charAt(i);
                    sb.append("##" + Integer.toHexString(c) + "##");
                }
            }

            return content;
        }
    }

    /**
     * 还原转义
     *
     * @param content
     * @return
     */
    public static String reConvertEmoji(String content) {
        if (StringUtils.isBlank(content)) {
            return "";
        } else {
            Pattern p = Pattern.compile("#{2}.{4}#{2}");
            Matcher m = p.matcher(content);

            while (m.find()) {
                String matcherStr = m.group();
                String cutStr = matcherStr.replace("##", "");
                if (!StringUtils.isBlank(cutStr)) {
                    StringBuffer string = new StringBuffer();
                    int data = Integer.parseInt(cutStr, 16);
                    string.append((char) data);
                    content = content.replace(matcherStr, string.toString());
                }
            }

            return content;
        }
    }

    /**
     * 将表情以replacement代替
     *
     * @param content
     * @return
     */
    public static String replaceEmoji(String content, String replacement) {
        if (StringUtils.isBlank(content)) {
            return "";
        } else {
            String temp = new String(content);

            String emo;
            StringBuffer sb;
            for (Matcher m = EMOJI_PATTERN.matcher(temp); m.find(); content = content.replace(emo, sb.toString())) {
                emo = m.group();
                sb = new StringBuffer();

                for (int i = 0; i < emo.length(); ++i) {
                    sb.append(replacement);
                }
            }

            return content;
        }
    }

    /**
     * 还原
     *
     * @param content
     * @param replacement
     * @return
     */
    public static String reReplaceEmoji(String content, String replacement) {
        if (StringUtils.isBlank(content)) {
            return "";
        } else {
            Pattern p = Pattern.compile("#{2}.{4}#{2}");

            String matcherStr;
            for (Matcher m = p.matcher(content); m.find(); content = content.replace(matcherStr, replacement)) {
                matcherStr = m.group();
            }

            return content;
        }
    }

    /**
     * 判断是否含有表情
     *
     * @param content
     * @return
     */
    public static boolean isEmojiNumber(String content) {
        if (StringUtils.isBlank(content)) {
            return false;
        } else {
            Pattern p = Pattern.compile("[0-9]#{2}(20e3)#{2}", 66);
            String temp = new String(content);
            Matcher m = p.matcher(temp);
            return m.find();
        }
    }

    public static void main(String[] args) {
        String content = "my name is Y东，O(∩_∩)O 哈哈 ㋀㏼  ██▓▒░";
        System.out.println(convertEmoji(content));
        System.out.println(reConvertEmoji(convertEmoji(content)));
        System.out.println(isEmojiNumber(convertEmoji(content)));
        String replacement = "?";
        System.out.println(replaceEmoji(content, replacement));
        System.out.println(isEmojiNumber(replaceEmoji(content, replacement)));
    }
}
