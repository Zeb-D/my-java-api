package com.yd.java.jdk.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * InetAddress 是 Java 对 IP 地址的封装
 * 这个类的实例经常和 UDP DatagramSockets 和 Socket，ServerSocket 类一起使用。
 *
 * @author Yd on 2018-06-28
 * @description
 */
public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("jenkov.com");
        System.out.println(address.getHostAddress());

        InetAddress address1 = InetAddress.getByName("78.46.84.171");
        System.out.println(address1.getHostAddress()+"->"+address);

        InetAddress address3 = InetAddress.getLocalHost();
        System.out.println(address3);
    }
}
