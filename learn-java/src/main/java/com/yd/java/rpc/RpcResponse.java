package com.yd.java.rpc;

import java.io.Serializable;
import java.lang.reflect.Member;

/**
 * 响应对象，用于封装接收到的消息，Member在通信框架是节点的抽象，这里用来表示来源节点。
 */
public class RpcResponse {
    private Member source;
    private Serializable message;

    public RpcResponse() {
    }

    public RpcResponse(Member source, Serializable message) {
        this.source = source;
        this.message = message;
    }

    public Member getSource() {
        return source;
    }

    public void setSource(Member source) {
        this.source = source;
    }

    public Serializable getMessage() {
        return message;
    }

    public void setMessage(Serializable message) {
        this.message = message;
    }
}