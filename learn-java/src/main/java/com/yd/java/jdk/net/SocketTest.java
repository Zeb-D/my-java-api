package com.yd.java.jdk.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 在Java中使用TCP/IP通过网络连接到服务器时，就需要创建java.net.Socket对象并连接到服务器。
 * 假如希望使用Java NIO，也可以创建Java NIO中的SocketChannel对象。
 *
 * @author Yd on 2018-06-27
 * @description
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("jenkov.com", 80);
        OutputStream out = socket.getOutputStream();

        out.write("some data".getBytes());
        out.flush();
//        out.close();//Socket is closed

        BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        String result =null;
        while ((result = reader.readLine()) !=null){
            System.out.println(result);
        }
        reader.close();

        socket.close();
    }
}
