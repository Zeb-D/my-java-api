package com.yd.java.rpc;

import java.io.Serializable;
import java.lang.reflect.Member;

public class MyRPC implements RpcCallback {
    public static void main(String[] args) {
        MyRPC myRPC = new MyRPC();
        byte[] rpcId = new byte[]{1, 1, 1, 1};
        byte[] key = new byte[]{0, 0, 0, 0};
        String message = "hello";
//        int sendOptions = Channel.SEND_OPTIONS_SYNCHRONIZED_ACK | Channel.SEND_OPTIONS_USE_ACK;
//        RpcMessage msg = new RpcMessage(rpcId, key, (Serializable) message);
//        RpcChannel rpcChannel = new RpcChannel(rpcId, channel, myRPC);
//        RpcResponse[] resp =
//                rpcChannel.send(channel.getMembers(), msg, RpcResponseType.FIRST_REPLY, sendOptions, 3000);
//        while (true)
//            Thread.currentThread().sleep(1000);
    }

    @Override
    public Serializable replyRequest(Serializable msg, Member sender) {
        RpcMessage mapmsg = (RpcMessage) msg;
        mapmsg.message = "hello,response for you!";
        return mapmsg;
    }

    @Override
    public void leftOver(Serializable msg, Member sender) {
        System.out.println("receive a leftover message!");
    }
}