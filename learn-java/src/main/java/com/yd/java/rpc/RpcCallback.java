package com.yd.java.rpc;


import java.io.Serializable;
import java.lang.reflect.Member;

/**
 * 定义一个RPC接口，这些方法是预留提供给上层具体逻辑处理的入口，replyRequest方法用于处理响应逻辑，leftOver方法用于残留请求的逻辑处理。
 */
public interface RpcCallback {
   public Serializable replyRequest(Serializable msg, Member sender);
   public void leftOver(Serializable msg, Member sender);  
}