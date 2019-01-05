package com.yd.java.rpc;

/**
 * 响应类型，提供多种唤起线程的条件，
 * 一共四种类型，分别表示接收到第一个响应就唤起线程、接收到集群中大多数节点的响应就唤起线程、接收到集群中所有节点的响应才唤起线程、无需等待响应的无响应模式。
 */
public class RpcResponseType {
 public static final int FIRST_REPLY = 1;  
 public static final int MAJORITY_REPLY = 2;  
 public static final int ALL_REPLY = 3;  
 public static final int NO_REPLY = 4;  
}