package com.yd.mq.activemq.common.activemq;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.io.Serializable;

/**
 * AMQ 消费者
 *
 * @author Yd on  2017-12-15
 **/
public interface AMQConsumerService {
    /**
     * 接受文本消息
     *
     * @param jmsTemplate
     * @param destination
     * @return
     * @throws JMSException
     */
    String receiveTextMessage(JmsTemplate jmsTemplate, Destination destination) throws JMSException;

    /**
     * 接受序列化对象消息
     *
     * @param jmsTemplate
     * @param destination
     * @return
     * @throws JMSException
     */
    <T extends Serializable> T receiveMessage(JmsTemplate jmsTemplate, Destination destination) throws JMSException;

    /**
     * 返回 已被强转的序列化对象
     *
     * @param jmsTemplate
     * @param destination
     * @param cls         需要接受的序列化的JavaBean
     * @param <T>
     * @return
     * @throws JMSException
     */
    <T extends Serializable> T receiveMessage(JmsTemplate jmsTemplate, Destination destination, Class<T> cls) throws JMSException;
}
