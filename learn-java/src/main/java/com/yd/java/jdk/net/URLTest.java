package com.yd.java.jdk.net;

import com.yd.java.jdk.Constant;
import sun.net.www.protocol.jar.JarURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * HTTP GET和POST
 * 从URLs到本地文件
 * URL类和URLConnection类。这两个类可以用来创建客户端到web服务器（HTTP服务器）的连接。
 *
 * @author Yd on 2018-06-27
 * @description
 */
public class URLTest {
    public static void main(String[] args) throws IOException {
        get();
        post();
        fileUrl();
        jarUrl();
    }

    public static void get() throws IOException {
        URL url = new URL("http://jenkov.com");
        URLConnection urlConnection = url.openConnection();
        InputStream input = urlConnection.getInputStream();
        int data = input.read();
        while (data != -1) {
            System.out.print((char) data);
            data = input.read();
        }
        input.close();
    }

    //默认情况下URLConnection发送一个HTTP GET请求到web服务器。
    // 如果你想发送一个HTTP POST请求，要调用URLConnection.setDoOutput(true)方法，
    public static void post() throws IOException {
        URL url = new URL("http://jenkov.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);//setDoOutput(true)，你就可以打开URLConnection的OutputStream
        OutputStream output = urlConnection.getOutputStream();
        output.write("hello".getBytes());
        InputStream input = urlConnection.getInputStream();
        int data = input.read();
        while (data != -1) {
            System.out.print((char) data);
            data = input.read();
        }
        input.close();
    }

    //URL也被叫做统一资源定位符。如果你的代码不关心文件是来自网络还是来自本地文件系统，URL类是另外一种打开文件的方式。
    public static void fileUrl() throws IOException {
        URL url = new URL("file:" + Constant.TEMP);//一定要加协议
        URLConnection urlConnection = url.openConnection();
        InputStream input = urlConnection.getInputStream();
        int data = input.read();
        while (data != -1) {
            System.out.print((char) data);
            data = input.read();
        }
        input.close();
    }

    //Java的JarURLConnection类用来连接Java Jar文件。一旦连接上，你可以获取Jar文件的信息。
    public static void jarUrl() throws IOException {
        String urlString = "http://butterfly.jenkov.com/"
                + "container/download/"
                + "jenkov-butterfly-container-2.9.9-beta.jar";

        URL jarUrl = new URL(urlString);
        JarURLConnection connection = new JarURLConnection(jarUrl, null);

        Manifest manifest = connection.getManifest();

        JarFile jarFile = connection.getJarFile();
    //do something with Jar file...
    }
}
