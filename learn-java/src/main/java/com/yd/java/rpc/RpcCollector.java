package com.yd.java.rpc;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;

/**
 * RPC响应集，用于存放同个UUID的所有响应。
 */
public class RpcCollector {
    public ArrayList<RpcResponse> responses = new ArrayList<RpcResponse>();
    public byte[] key;
    public int options;
    public int destcnt;

    public RpcCollector(byte[] key, int options, int destcnt) {
        this.key = key;
        this.options = options;
        this.destcnt = destcnt;
    }

    public void addResponse(Serializable message, Member sender) {
        RpcResponse resp = new RpcResponse(sender, message);
        responses.add(resp);
    }

    public boolean isComplete() {
        if (destcnt <= 0) return true;
        switch (options) {
            case RpcResponseType.ALL_REPLY:
                return destcnt == responses.size();
            case RpcResponseType.MAJORITY_REPLY: {
                float perc = ((float) responses.size()) / ((float) destcnt);
                return perc >= 0.50f;
            }
            case RpcResponseType.FIRST_REPLY:
                return responses.size() > 0;
            default:
                return false;
        }
    }

    public RpcResponse[] getResponses() {
        return responses.toArray(new RpcResponse[responses.size()]);
    }
}