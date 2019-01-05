package com.yd.java.jdk.instrument;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * ClassFileTransformer需要实现transform方法，根据自己的功能需要修改class字节码，在修改字节码过程中需要借助javassist进行字节码编辑。
 *
 * @author Yd on  2018-06-28
 * @description javassist是一个开源的分析、编辑和创建java字节码的类库。通过使用javassist对字节码操作可以实现动态”AOP”框架。
 * 关于java字节码的处理，目前有很多工具，如bcel，asm(cglib只是对asm又封装了一层)。不过这些都需要直接跟虚拟机指令打交道。
 * javassist的主要的优点，在于简单，而且快速，直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。
 **/
public class AopAgentTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool = null;
        CtClass cl = null;
        try {
            pool = ClassPool.getDefault();

            cl = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

//            CtMethod aop_method = pool.get("com.jdktest.instrument.AopMethods").
//                    getDeclaredMethod("aopMethod");
//            System.out.println(aop_method.getLongName());

            CodeConverter convert = new CodeConverter();

            if (cl.isInterface() == false) {
                CtMethod[] methods = cl.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        AOPInsertMethod(methods[i]);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className
                    + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    private void AOPInsertMethod(CtMethod method) throws NotFoundException,CannotCompileException {
        //situation 1:添加监控时间
        method.instrument(new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName()
                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
        //situation 2:在方法体前后语句
//      method.insertBefore("System.out.println(\"enter method\");");
//      method.insertAfter("System.out.println(\"leave method\");");
    }
}
