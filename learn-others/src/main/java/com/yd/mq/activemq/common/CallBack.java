package com.yd.mq.activemq.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yd on  2018-01-16
 **/
public interface CallBack {

    List<? extends Serializable> getMessage(Integer length);
}
