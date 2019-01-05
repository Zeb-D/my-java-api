package com.yd.java.jdk.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//Udp 客户端
public class DatagramClient {
    public static void main(String args[]) {
        String host = "localhost";
        if (args.length == 1)
            host = args[0];
        DatagramPacket dgp1;
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
            byte[] buffer;//用来存储发送的数据
            buffer = new String("hello").getBytes();//字符串转化数组
            //将主机名转变为InetAddress类对象，此对象存储有ip对象和地址
            InetAddress ia = InetAddress.getByName(host);
            //创建一个DatagramPacket对象来封装字节数组的指针以及目标地址信息，目标地址包含了ip和端口号
            DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, ia, 10000);
            s.send(dgp);
            byte[] buffer2 = new byte[50];
            //创建一个对象来封装返回来的数据
            dgp1 = new DatagramPacket(buffer2, buffer.length, ia, 10000);
            s.receive(dgp1);
            System.out.println("server:" + new String(dgp1.getData()));
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            if (s != null)
                s.close();
        }
    }
}