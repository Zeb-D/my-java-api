package com.yd.java.concurrency.singletonCase;

/**
 * 惰性初始化holder 类 ,避免了同步带来的性能影响。
 * @author Yd on  2018-05-12
 * @description
 * 内部类的加载：内部类（不论是静态内部类还是非静态内部类）都是在第一次使用时才会被加载。达到了懒汉的效果；
 * 类加载的时候有一种机制叫做 缓存机制；第一次加载成功之后会被缓存起来；而且一般一个类不会加载多次
 **/
public class ObjectFactory {

    private static class ObjectHolder{
        public static Object object = new Object();
    }

    public static Object getObject(){
        return ObjectHolder.object;
    }
}
