package com.yd.java.rpc;

import java.io.Serializable;
import java.lang.reflect.Member;

/**
 * @author Yd on  2018-09-03
 * @description
 **/
public interface ChannelListener {
    void messageReceived(Serializable msg, Member sender);

    boolean accept(Serializable msg, Member sender);
}
