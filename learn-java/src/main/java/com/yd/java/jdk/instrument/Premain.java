package com.yd.java.jdk.instrument;

import javassist.convert.Transformer;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Premain {
    public static void premain(String agentArgs,Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException {
//        ClassDefinition def = new ClassDefinition(TransClass.class, Transformer
//                .getBytesFromFile(Transformer.classNumberReturns2));
//        inst.redefineClasses(new ClassDefinition[] { def });
        System.out.println("success"); 
    } 
 }