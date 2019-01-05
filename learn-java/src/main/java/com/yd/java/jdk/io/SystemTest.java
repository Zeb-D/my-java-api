package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.*;

/**
 * System.in, System.out, System.err
 * System.in, System.out, System.err这3个流同样是常见的数据来源和数据流目的地。
 * JVM启动的时候通过Java运行时初始化这3个流，所以你不需要初始化它们(尽管你可以在运行时替换掉它们)。
 * 是java.lang.System类中的静态成员
 * 只需要把一个新的InputStream设置给System.in或者一个新的OutputStream设置给System.out或者System.err
 *
 * @author Yd on  2018-06-22
 * @description 可以使用System.setIn(), System.setOut(), System.setErr()方法设置新的系统流
 * (译者注：这三个方法均为静态方法，内部调用了本地native方法重新设置系统流)。
 **/
public class SystemTest {

    //System.in是一个典型的连接控制台程序和键盘输入的InputStream流。
    // 通常当数据通过命令行参数或者配置文件传递给命令行Java程序的时候，System.in并不是很常用。
    // 图形界面程序通过界面传递参数给程序，这是一块单独的Java IO输入机制。
    public void in() throws FileNotFoundException {
//        System.in(new FileInputStream(new File(Constant.FILENAME)));
    }

    public static void out() throws IOException {
        OutputStream output = new FileOutputStream(Constant.TEMP);

        PrintStream printOut = new PrintStream(output);
        System.setOut(printOut);
        System.out.println("hello-world!!!");
        System.out.flush();
    }

    public static void main(String[] args) throws IOException {
        out();
    }
}
