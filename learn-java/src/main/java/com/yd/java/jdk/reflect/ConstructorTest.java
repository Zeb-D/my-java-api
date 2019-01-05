package com.yd.java.jdk.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Yd on 2018-07-04
 * @description
 */
public class ConstructorTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class aClass = ConstructorTest.class;//获取Class对象
        Constructor constructor =aClass.getConstructor(new Class[]{});

        //获取指定构造方法的方法参数信息
        Class[] parameterTypes = constructor.getParameterTypes();
        System.out.println(parameterTypes.length);

        ConstructorTest constructorTest = (ConstructorTest) constructor.newInstance();
        System.out.println(constructorTest);
    }
}
