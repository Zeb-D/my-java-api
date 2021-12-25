package com.yd.javassist;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class TestMain {

    public static void main(String[] args) {
        Class<?> clazz = TestMain.class;
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod("test");

            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int len = ctMethod.getParameterTypes().length;
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                System.out.print("test : ");
                for (int i = 0; i < len; i++) {
                    System.out.print(attr.variableName(i + pos) + ' ');
                }
                System.out.println();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void test(String param1, int intParam2) {
        System.out.println(param1 + intParam2);
    }
}
