package com.yd.mq.activemq.common.activemq;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * AMQ生产者服务
 * @author Yd on  2017-12-15
 **/
public interface AMQProducerService {

    /**
     * 发送序列化对象
     * @param jmsTemplate
     * @param destination
     * @param message
     */
    void sendMessage(JmsTemplate jmsTemplate, Destination destination, Serializable message);
}
