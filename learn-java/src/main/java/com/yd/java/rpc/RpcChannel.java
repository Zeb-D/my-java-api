package com.yd.java.rpc;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.nio.channels.Channel;
import java.util.Arrays;
import java.util.HashMap;

public class RpcChannel implements ChannelListener {
    private Channel channel;
    private RpcCallback callback;
    private byte[] rpcId;
    private int replyMessageOptions = 0;
    private HashMap<byte[], RpcCollector> responseMap = new HashMap<byte[], RpcCollector>();

    public RpcChannel(byte[] rpcId, Channel channel, RpcCallback callback) {
        this.rpcId = rpcId;
        this.channel = channel;
        this.callback = callback;
//        channel.addChannelListener(this);
    }

//    public RpcResponse[] send(Member[] destination, Serializable message, int rpcOptions,
//                              int channelOptions, long timeout) throws ChannelException {
//        int sendOptions = channelOptions & ~Channel.SEND_OPTIONS_SYNCHRONIZED_ACK;
//        byte[] key = UUIDGenerator.randomUUID(false);
//        RpcCollector collector = new RpcCollector(key, rpcOptions, destination.length);
//        try {
//            synchronized (collector) {
//                if (rpcOptions != RpcResponseType.NO_REPLY) responseMap.put(key, collector);
//                RpcMessage rmsg = new RpcMessage(rpcId, key, message);
//                channel.send(destination, rmsg, sendOptions);
//                if (rpcOptions != RpcResponseType.NO_REPLY) collector.wait(timeout);
//            }
//        } catch (InterruptedException ix) {
//            Thread.currentThread().interrupt();
//        } finally {
//            responseMap.remove(key);
//        }
//        return collector.getResponses();
//    }

    @Override
    public void messageReceived(Serializable msg, Member sender) {
        RpcMessage rmsg = (RpcMessage) msg;
        byte[] key = rmsg.uuid;
        if (rmsg.reply) {
            RpcCollector collector = responseMap.get(key);
            if (collector == null) {
                callback.leftOver(rmsg.message, sender);
            } else {

                synchronized (collector) {
                    if (responseMap.containsKey(key)) {
                        collector.addResponse(rmsg.message, sender);
                        if (collector.isComplete()) collector.notifyAll();
                    } else {
                        callback.leftOver(rmsg.message, sender);
                    }
                }
            }
        } else {
            Serializable reply = callback.replyRequest(rmsg.message, sender);
            rmsg.reply = true;
            rmsg.message = reply;
            try {
//                channel.send(new Member[]{sender}, rmsg,
//                        replyMessageOptions & ~Channel.SEND_OPTIONS_SYNCHRONIZED_ACK);
            } catch (Exception x) {
            }
        }
    }

    @Override
    public boolean accept(Serializable msg, Member sender) {
        if (msg instanceof RpcMessage) {
            RpcMessage rmsg = (RpcMessage) msg;
            return Arrays.equals(rmsg.rpcId, rpcId);
        } else
            return false;
    }
}