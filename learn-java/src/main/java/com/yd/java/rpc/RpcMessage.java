package com.yd.java.rpc;

import java.io.*;

/**
 * 定义通信消息协议，实现Externalizable接口自定义序列化和反序列化，
 * message用于存放响应消息，uuid标识用于关联线程，rpcId用于标识RPC实例，reply表示是否回复。
 */
public class RpcMessage implements Externalizable {
    protected Serializable message;
    protected byte[] uuid;
    protected byte[] rpcId;
    protected boolean reply = false;

    public RpcMessage() {
    }

    public RpcMessage(byte[] rpcId, byte[] uuid, Serializable message) {
        this.rpcId = rpcId;
        this.uuid = uuid;
        this.message = message;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        reply = in.readBoolean();
        int length = in.readInt();
        uuid = new byte[length];
        in.readFully(uuid);
        length = in.readInt();
        rpcId = new byte[length];
        in.readFully(rpcId);
        message = (Serializable) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(reply);
        out.writeInt(uuid.length);
        out.write(uuid, 0, uuid.length);
        out.writeInt(rpcId.length);
        out.write(rpcId, 0, rpcId.length);
        out.writeObject(message);
    }
}