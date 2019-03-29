package com.yd.java.encrypt.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 泛型工具类
 */
public class GenricTypeUtil {

    /**
     * 获取该类泛型类型
     *
     * @param clazz
     * @param index
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Class<Object> getSuperClassGenricType(Class clazz, int index) {

        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        // 返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static void main(String[] args) {
        System.out.println(getSuperClassGenricType(List.class,0));
    }
}
