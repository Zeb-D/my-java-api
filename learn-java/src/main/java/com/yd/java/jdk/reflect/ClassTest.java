package com.yd.java.jdk.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class api 使用
 * @author Yd on 2018-07-04
 * @description
 */
public class ClassTest {

    public static void main(String[] args) throws ClassNotFoundException {
        String className = ClassTest.class.getName();
        Class clazz = Class.forName(className);
        clazz.getProtectionDomain().getCodeSource().getLocation();
        //访问一个类的修饰符
        int modifiers = clazz.getModifiers();
        System.out.println(Modifier.isPublic(modifiers));

        //获取包信息
        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);

        //访问类的父类
        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
        System.out.println(superclass instanceof Object);

        //获取指定类所实现的接口集合
        Class[] interfaces = clazz.getInterfaces();
        System.out.println(interfaces.length);

        //访问一个类的构造方法
        Constructor[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);

        //访问一个类的所有方法
        Method[] methods = clazz.getMethods();
        System.out.println(methods.length);

        //访问一个类的成员变量
        Field[] fields = clazz.getFields();
        System.out.println(fields.length);

        //访问一个类的注解
        Annotation[] annotations = clazz.getAnnotations();
        System.out.println(annotations.length);
    }
}
