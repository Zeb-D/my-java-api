package com.yd.java.encrypt;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 设计一种需要双方进行认证的协议
 * 服务端，掌握大于100小于100N的质数池，
 * 根据每个机器自身硬件的mac地址，进行分配两个固定的质数供 服务端&客户端 作为公钥使用
 * 同时服务端根据【对方机器及自身机器mac】产生一个质数作为 对该机器的密钥使用
 * 客户端 根据【自己会自身硬件的mac地址】产生一个小于100的质数 作为自身密钥使用
 * <p>
 * 公钥 记为p、g
 * 服务端私钥记为 a
 * 客户端私钥记为 b
 * <p>
 * <p>
 * 接下来【建立】通信流程：
 * 客户端 带着mac地址 向
 * 服务端 产生【有时效性】的两个公钥p&g、自身的私钥b、及产生一个数字B
 * 客户端 拿着两个公钥p&g、自身随机产生的私钥、产生一个数字A，及checkB
 * 客户端 把数字A、checkB 往服务端，
 * 服务端根据数字A产生checkA，校验 checkA == checkB
 * 最后服务端将【mac+数字A+checkB】 作为请求id 返回给客户端
 * 客户端以后请求带上请求id
 * <p>
 * 校验请求id流程：
 * 服务端将请求id，拆成mac+数字A+checkB
 * 由mac得到【有时效性】的两个公钥p&g、自身的私钥b，
 * 根据数字A产生checkA，校验checkA == checkB，通过认为该段时间请求合法
 * <p>
 * <p>
 * 具体交互算法如下
 *
 * @author created by Zeb灬D on 2020-03-19 19:37
 */
public class DiffieHellman {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        //以下数字均为质数
        int p = 23, g = 5; // 非秘密信息，双方约定
        int a = 5, b = 7;//A、B两人各自掌握的信息
        double A = Math.pow(g, a) % p;// 20.0
        double B = Math.pow(g, b) % p; // 17.0

        double checkB = Math.pow(B, a) % p; // 21.0
        double checkA = Math.pow(A, b) % p; // 21.0

        System.out.println(A + " -> " + B);
        //校验双方信息是否一致
        System.out.println(checkA + " -> " + checkB);
        //通过计算机的mac进行分配p&g

        //得到IP，输出PC-201309011313/122.206.73.83
//        InetAddress ia = InetAddress.getLocalHost();
//        System.out.println(ia);
//        getLocalMac(ia);
    }
//
//    private static void getLocalMac(InetAddress ia) throws SocketException {
//        //获取网卡，获取地址
//        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
//        System.out.println("mac数组长度：" + mac.length);
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < mac.length; i++) {
//            if (i != 0) {
//                sb.append("-");
//            }
//            //字节转换为整数
//            int temp = mac[i] & 0xff;
//            String str = Integer.toHexString(temp);
//            System.out.println("每8位:" + str);
//            if (str.length() == 1) {
//                sb.append("0" + str);
//            } else {
//                sb.append(str);
//            }
//        }
//        System.out.println("本机MAC地址:" + sb.toString().toUpperCase());
//        System.out.println(new String(mac));
//    }
}
