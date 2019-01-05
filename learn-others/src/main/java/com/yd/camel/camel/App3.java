package com.yd.camel.camel;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App3 {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-camel-ftp2jms.xml");
        context.start();
        System.in.read();
    }
}