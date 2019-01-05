package com.yd.java.jdk.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;

class UdpReceive {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(10001);
        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            String ip = dp.getAddress().getHostAddress();
            String data = new String(dp.getData(), 0,dp.getLength(), Charset.forName("UTF-8"));
            System.out.println(ip + "::" + data);
        }
    }
}  