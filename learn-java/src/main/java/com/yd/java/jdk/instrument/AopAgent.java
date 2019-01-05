package com.yd.java.jdk.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * 平常在统计方法执行的耗时时长时，一般都是在方法的开头和结尾通过System.currentTimeMillis()计算耗时，这样不得不在每个方法中都重复这样的操作，现在使用Instrument，可以优雅的实现该功能。
 * @author Yd on  2018-06-28
 * @description
 **/
public class AopAgent {
    static private Instrumentation _inst = null;

    /**
     * The agent class must implement a public static premain method similar in principle to the main application entry point.
     * After the Java Virtual Machine (JVM) has initialized,
     * each premain method will be called in the order the agents were specified,
     * then the real application main method will be called.
     **/
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("AopAgentTest.premain() was called.");

        /* Provides services that allow Java programming language agents to instrument programs running on the JVM.*/
        _inst = inst;

        /* ClassFileTransformer : An agent provides an implementation of this interface in order to transform class files.*/
        ClassFileTransformer trans = new AopAgentTransformer();

        System.out.println("Adding a AopAgentTest instance to the JVM.");

        /*Registers the supplied transformer.*/
        _inst.addTransformer(trans);
    }
}
