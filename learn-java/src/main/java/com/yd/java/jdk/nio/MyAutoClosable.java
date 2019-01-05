package com.yd.java.jdk.nio;

/**
 * 测试自动关闭流
 * @author Yd on 2018-06-22
 */
public class MyAutoClosable implements AutoCloseable {

    public void doIt() {
        System.out.println("MyAutoClosable doing it!");
    }

    @Override
    public void close() throws Exception {
        System.out.println("MyAutoClosable closed!");
    }
}