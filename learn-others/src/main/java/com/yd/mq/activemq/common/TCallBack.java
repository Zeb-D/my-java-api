package com.yd.mq.activemq.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yd on  2018-01-16
 **/
public interface TCallBack {

    <T extends Serializable> List<T> getMessage(Integer length);

    <T extends Serializable> T getMessage();
}
