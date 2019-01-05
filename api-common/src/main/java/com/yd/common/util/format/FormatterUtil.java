package com.yd.common.util.format;

import com.yd.common.EnumSummry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

/**
 * @author Yd on  2018-01-27
 * @Description：
 **/
@Component
public class FormatterUtil implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(FormatterUtil.class);
    static char[] hex = "0123456789ABCDEF".toCharArray();
    private static ApplicationContext applicationContext;

    public static <T> T getApplicationContext(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }

    public static <T extends BaseFormatEnum> String format(String key, T baseFormatEnum) {
        return baseFormatEnum.format(key);
    }

    public static <T extends Enum<T>> String format(String key, Class<T> en) {
//        en.getEnumConstants()[0].toString();
        return T.valueOf(en, key).toString();
    }

    /**
     * 单个Entity格式化
     *
     * @param formatDesc
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T format(String formatDesc, T t) {
        if (t == null) {
            return t;
        }
        String[] descs = getDescriptions(formatDesc);
        for (int i = 0; i < descs.length; i++) {
            formatObject(t, descs[i]);
        }
        return t;
    }

    /**
     * 多个Entity格式化
     *
     * @param formatDesc
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> format(String formatDesc, List<T> ts) {
        if (CollectionUtils.isEmpty(ts)) {
            return ts;
        }
        for (T t : ts) {
            t = format(formatDesc, t);
        }
        return ts;
    }

    public static void formatObject(Object object, String desc) {
        if (desc.startsWith("{")) {
            desc = decodeExpr(desc);
            String[] args = decodeExprArgs(desc);
            String formatterName = args[0];
            String filedName = args[1];
            //格式化替换
            formatObject(object, getDictionaryFormatter(formatterName), filedName);
        } else {
            return;
        }

    }

    public static void formatObject(Object obj, DictionaryFormatter formatter, String filedName) {
        if (obj == null) {
            return;
        }
        try {
            Class clazz = obj.getClass();
            Field field = obj.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, formatter.format(String.valueOf(field.get(obj))));
        } catch (Exception e) {
            log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " Method has Error:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String key = "Y";
        String value = EnumSummry.YorN.valueOf(key).getValue();
        String va1 = format(key, EnumSummry.YorN.N);
        String va2 = format(key, EnumSummry.YorN.class);
    }

    private static String[] getDescriptions(String descriptions) {
        String[] arr = descriptions.split(",");
        return arr;
    }

    public static DictionaryFormatter getDictionaryFormatter(String beanName) {
        Object formatter = null;
        try {
            formatter = applicationContext.getBean(beanName);
        } catch (Exception e) {
            log.error("Not Found 指定的 " + beanName + " 格式化器！");
        }
        if (formatter != null) {
            return (DictionaryFormatter) applicationContext.getBean(beanName);
        }
        throw new RuntimeException("Not Found You Need Formatter of" + beanName);
    }

    public static String escapseString(Object obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer("");
        CharacterIterator it = new StringCharacterIterator(obj.toString());
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            if (c == '"') {
                sb.append("\\\"");
            } else if (c == '\\') {
                sb.append("\\\\");
            } else if (c == '/') {
                sb.append("\\/");
            } else if (c == '\b') {
                sb.append("\\b");
            } else if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\t') {
                sb.append("\\t");
            } else if (Character.isISOControl(c)) {
                sb.append("\\u");
                int n = c;
                for (int i = 0; i < 4; ++i) {
                    int digit = (n & 0xf000) >> 12;
                    sb.append(hex[digit]);
                    n <<= 4;
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static String decodeExpr(String str) {
        return str.substring(1, str.length() - 1);
    }

    private static String[] decodeExprArgs(String str) {
        String[] args = new String[2];
        args[0] = str.substring(0, str.indexOf(":"));
        args[1] = str.substring(str.indexOf(":") + 1);
        return args;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FormatterUtil.applicationContext = applicationContext;
    }
}
