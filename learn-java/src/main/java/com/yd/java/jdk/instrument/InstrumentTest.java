package com.yd.java.jdk.instrument;

import java.util.Date;

/**
 * @author Yd on  2018-06-28
 * @description
 **/
public class InstrumentTest {
    public void sayHello() {
        System.out.println(Thread.currentThread().getName()+new Date());
    }

    //java -javaagent:E:/workspace/MyInstrument/target/MyInstrument-0.0.1-SNAPSHOT.jar -cp E:/workspace/SayHello/target/SayHello-0.0.1-SNAPSHOT.jar com.jdktest.SayHello.Target
    public static void main(String[] args) {
        new InstrumentTest().sayHello();
    }
}
