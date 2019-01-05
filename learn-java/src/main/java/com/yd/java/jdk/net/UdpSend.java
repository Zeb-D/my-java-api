package com.yd.java.jdk.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

/*
思路： 
 1）定义udpsocket服务。通常会监听一个端口。其实就是给这个接收网络应用程序定义数字标识。 
 方便于明确哪些数据过来该应用程序可以处理。 
 2）定义一个数据包，因为要存储接收到的字节数据。 
 因为数据包对象中有更多功能可以提取字节数据中的不同数据信息。 
 3）通过socket服务的receive方法将收到的数据存入已定义好的数据包中。 
 4）通过数据包对象的特有功能。将这些不同的数据取出。打印在控制台上。 
 5）关闭资源。 
*/
class UdpSend {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        BufferedReader bufr =
                new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

        String line = null;
        while ((line = bufr.readLine()) != null) {
            if ("886".equals(line))
                break;
            byte[] buf = line.getBytes();
            DatagramPacket dp =
                    new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), 10001);
            ds.send(dp);
        }
        ds.close();
    }
}