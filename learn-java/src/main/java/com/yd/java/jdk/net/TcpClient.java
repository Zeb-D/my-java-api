package com.yd.java.jdk.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
需求：建立一个文本转换服务器。
 客户端给服务端发送文本，服务端会将文本转成大写再返回给客户端。
 而且客户端可以不断的进行文本转换，当客户端输入over时，转换结束。
 分析：
 客户端：
 既然是操作设备上的数据，那么就可以使用io技术，并按照io的操作规律来思考。
 源：键盘录入。
 目的：网络设备，网络输出流。
 而且操作的是文本数据。可以选择字符流。
*/
class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 10002);
        //键盘录入
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        //获取网络输出流
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        //获取网络输入流
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while ((line = buf.readLine()) != null) {
            if ("over".equals(line))
                break;
            out.println(line);
            String str = bufIn.readLine();
            System.out.println("server:" + str);
        }
    }
} 