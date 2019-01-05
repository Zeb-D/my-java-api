package com.yd.java.jdk.generic;

import com.yd.entity.User;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author Yd on  2018-05-07
 * @description 测试泛型参数
 **/
public class TestGenericParamType {

    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        ParameterizedType type = (ParameterizedType) users.getClass().getGenericSuperclass();
        System.out.println(type.getActualTypeArguments());
        Arrays.stream(type.getActualTypeArguments()).forEach(type1 -> {
            System.out.println(type1+"--"+type1.getClass()+"--"+type1.getTypeName());
        });

    }

}
