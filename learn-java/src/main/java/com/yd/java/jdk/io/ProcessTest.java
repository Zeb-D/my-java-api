package com.yd.java.jdk.io;

import ch.qos.logback.core.OutputStreamAppender;

import java.io.*;
import java.util.Map;

/**
 * 进程
 * <p>
 * 进程具有一个独立的执行环境。通常情况下，进程拥有一个完整的、私有的基本运行资源集合。特别地，每个进程都有自己的内存空间。
 * 进程往往被看作是程序或应用的代名词，然而，用户看到的一个单独的应用程序实际上可能是一组相互协作的进程集合。
 * 为了便于进程之间的通信，大多数操作系统都支持进程间通信（IPC），如pipes 和sockets。IPC不仅支持同一系统上的通信，也支持不同的系统。
 * Java虚拟机的大多数实现是单进程的。Java应用可以使用的ProcessBuilder对象创建额外的进程，多进程应用超出了本课的范围。
 * <p>
 * 线程
 * <p>
 * 线程有时也被称为轻量级的进程。进程和线程都提供了一个执行环境，但创建一个新的线程比创建一个新的进程需要的资源要少。
 * 线程是在进程中存在的 — 每个进程最少有一个线程。线程共享进程的资源，包括内存和打开的文件。这样提高了效率，但潜在的问题就是线程间的通信。
 * 多线程的执行是Java平台的一个基本特征。
 * 每个应用都至少有一个线程 – 或几个，如果算上“系统”线程的话，比如内存管理和信号处理等。
 * 但是从程序员的角度来看，启动的只有一个线程，叫主线程。
 *
 * @author mq on 2018-06-22
 */
public class ProcessTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        assert environment != null;

        Runtime runtime =Runtime.getRuntime();

        //注意，各个os支持的命令不一样
        Process process = Runtime.getRuntime().exec(new String[]{"ls","-l"});//ping www.baidu.com
        InputStream input = process.getInputStream();//取得命令结果的输出流

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String result = null;
        while ((result=reader.readLine())!=null){
            System.out.println(result);//输出执行命令后的结果
        }

        System.out.println(String.format("total men :%s",runtime.totalMemory()));
//        process.waitFor();
        process.destroyForcibly();
    }
}
