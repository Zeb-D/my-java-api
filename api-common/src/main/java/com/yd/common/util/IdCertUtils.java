package com.yd.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份认证工具类.
 *
 * @author Yd on 2019-01-07 下午2:20
 **/
public class IdCertUtils {

//    public static final Pattern REGEX_ID_NO_18 = Pattern.compile("(\\d{17}[\\d|X])|\\d{15}");

    /**
     * 18位二代身份证号码的正则表达式
     */
    public static final Pattern REGEX_ID_NO_18 = Pattern.compile("^"
            + "\\d{6}" // 6位地区码
            + "(18|19|([23]\\d))\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}" // 3位顺序码
            + "[0-9Xx]" // 校验码
            + "$");

    /**
     * 15位一代身份证号码的正则表达式
     */
    public static final Pattern REGEX_ID_NO_15 = Pattern.compile("^"
            + "\\d{6}" // 6位地区码
            + "\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}"// 3位顺序码
            + "$");

    /**
     * 18位身份证校验 1-17位乘积 因子
     */
    public static final String[] powers = new String[]{"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
    /**
     *
     */
    public static final String[] parityBit = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * 15位身份证号常量
     */
    private static final int ID_NUM_15 = 15;

    /**
     * 18位身份证号常量
     */
    private static final int ID_NUM_18 = 18;

    /**
     * 通过身份证号计算出生日期.
     *
     * @param cardId 有效的身份证号
     * @return
     */
    public static Date cardIdtoBirthday(String cardId) {
        if (StringUtils.isBlank(cardId) || !idCardValidate(cardId)) {
            return null;
        }
        String birthdayStr = "";
        cardId = cardId.trim();
        //身份证位数18
        if (cardId.length() == ID_NUM_18) {
            birthdayStr = cardId.substring(6, 14);
        }
        // 身份证位数15
        if (cardId.length() == ID_NUM_15) {
            birthdayStr = "19" + cardId.substring(6, 12);
        }
        if (StringUtils.isBlank(birthdayStr) || !StringUtils.isNumeric(birthdayStr)) {
            return null;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(birthdayStr.substring(0, 4)));
            calendar.set(Calendar.MONTH, Integer.valueOf(birthdayStr.substring(4, 6)) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(birthdayStr.substring(6)));
            return calendar.getTime();
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * 通过身份证号计算性别.
     *
     * @param idCard 身份证号
     * @return 0:男或者1:女
     */
    public static Integer getSex(String idCard) {
        if (StringUtils.isBlank(idCard) || !idCardValidate(idCard)) {
            return null;
        }
        if (idCard.length() == ID_NUM_15) {
            String sex = idCard.substring(idCard.length() - 3, idCard.length());
            int sexNum = Integer.parseInt(sex) % 2;
            return sexNum == 0 ? 1 : 0;
        }
        if (idCard.length() == ID_NUM_18) {
            String sex = idCard.substring(idCard.length() - 4, idCard.length() - 1);
            int sexNum = Integer.parseInt(sex) % 2;
            return sexNum == 0 ? 1 : 0;
        }
        return 2;
    }

    /**
     * 判断身份证号格式是否正确.
     * <pre>
     *     规则
     *     1、18位身份证1-17位，每位乘以对应校验位的和
     *     2、模11后 的位数 与18位进行校验
     * </pre>
     *
     * @param idCard
     * @return
     */
    public static boolean idCardValidate(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }

        Matcher mat = REGEX_ID_NO_18.matcher(idCard);
        if (!mat.find()) {
            return false;
        }
        if (idCard.length() == 18) {
            String _num = idCard.substring(0, 17);
            int _power = 0;
            if (!NumberUtils.isNumber(_num)) {
                return false;
            }
            for (int i = 0; i < 17; i++) {
                _power += Integer.parseInt(String.valueOf(_num.charAt(i))) * Integer.parseInt(powers[i]);
            }
            if (parityBit[_power % 11].equals(String.valueOf(idCard.charAt(17)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是有效的中文名
     * <em>JDK7 and above</em>
     * <p>长度为1-30的中文</p>
     *
     * @param name
     * @return <p>null - false</p>
     * <p>"" - false</p>
     * <p>"赵C" - false</p>
     * <p>"哈哈哈" - true</p>
     * <p>"發財" - true</p>
     */
    public static boolean isValidChineseName(String name) {
        return StringUtils.isNotEmpty(name) && Pattern.matches("[\\u4E00-\\u9FA5]{1,20}(?:[·|\\.][\\u4E00-\\u9FA5]{1,20})*", name);
    }

    public static void main(String[] args) {
        String id = "123456789012345678";
        System.out.println(idCardValidate(id));
        System.out.println(idCardValidate("123456789012345677"));
        System.out.println(isValidChineseName("發財"));
    }

}
