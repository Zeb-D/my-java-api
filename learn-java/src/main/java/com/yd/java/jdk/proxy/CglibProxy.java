package com.yd.java.jdk.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * cglib动态代理,原理：在CGLIB底层，其实是借助了ASM这个非常强大的Java字节码生成框架。
 *
 * @author Yd on  2018-06-13
 * @description
 **/
public class CglibProxy implements MethodInterceptor {
    private final static Logger LOGGER = LoggerFactory.getLogger(CglibProxy.class);
    Enhancer enhancer = new Enhancer();
    private Object target;

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前工作区间
        System.out.println(CglibProxy.class.getResource(""));//当前类文件的URI目录
        //开启字节码生成目录
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, CglibProxy.class.getResource("").getPath());
        CglibProxy proxy = new CglibProxy();
        HashMap hashMap = (HashMap) proxy.getInstance(new HashMap<>());
        hashMap.put("a", "a");
        hashMap.get("a");
    }

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        LOGGER.info("before");
//        Object invoke = methodProxy.invoke(o, objects);//这种方式会报错
        Object invoke = methodProxy.invoke(this.target, objects);
        //Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println(invoke);
        LOGGER.info("after");
        return invoke;
    }
}
