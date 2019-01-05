package com.yd.java.jdk.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//UDP服务端
public class DatagramServer {

    public static void main(String args[]) throws IOException {
        System.out.println("Server starting...\n");
        //在端口号10000上创建套接字，从用户程序发送包到这个端口
        DatagramSocket s = new DatagramSocket(10000);
        byte[] data = new byte[50];
        DatagramPacket dgp = new DatagramPacket(data, data.length);
        while (true) {
            s.receive(dgp);
            System.out.println("client:" + new String(data));
            s.send(dgp);
        }
    }
}