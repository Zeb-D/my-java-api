package com.yd.java.jdk.nio;

import com.yd.java.jdk.Constant;

import java.io.*;

/**
 * java7新特性 自动关闭 任何实现Closable
 * 原理：AutoCloseable接口对JDK7新添加的带资源的try语句提供了支持，这种try语句可以自动执行资源关闭过程。Closeable扩展了AutoCloseable
 * 实现了Flushable接口的类的对象，可以强制将缓存的输出写入到与对象关联的流中。
 *
 * @author Yd on  2018-06-20
 * @description <p>注：关于带资源的try语句的3个关键点：
 * 由带资源的try语句管理的资源必须是实现了AutoCloseable接口的类的对象。
 * <p>
 * 在try代码中声明的资源被隐式声明为final。
 * <p>
 * 通过使用分号分隔每个声明可以管理多个资源。
 **/
public class ClosableTest {

    public static void main(String[] args) throws Exception {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File(Constant.FILENAME)), "UTF8"), 1024)
        ) {
            System.out.println(reader.readLine());    //这里直接读一行
            //无需调用 reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try(Closeable variable1,2,3){}catch (Exception e){} //调用格式
        try (MyAutoClosable myAutoClosable = new MyAutoClosable()) {
            myAutoClosable.doIt();
        }
    }
}
