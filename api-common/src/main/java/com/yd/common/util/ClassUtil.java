package com.yd.common.util;

import com.yd.entity.User;
import com.yd.entity.UserList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author Yd on  2018-05-07
 * @description 对Class 进行相关操作的util
 **/
public class ClassUtil {

    /**
     * 获取某个class的泛型参数
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> String getGenericClassName(Class<T> tClass){
        System.out.println(tClass.getGenericSuperclass());
        Type type = tClass.getGenericSuperclass();
        if (type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Arrays.stream(parameterizedType.getActualTypeArguments()).forEach(type1 -> {
                System.out.println(type1.getClass()+"--"+type1.getTypeName());
            });
        }
        return ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
    }

    public static Class classForName(String className){
        try {
            Class<?> tClass = Class.forName(className);
            return  tClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Not Found This ClassName");
        }
    }

    public static void main(String[] args) {
        String className = getGenericClassName(UserList.class);
        System.out.println(className);
        Class clazz = classForName(className);
        System.out.println(clazz);
    }
}
