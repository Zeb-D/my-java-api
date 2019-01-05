package com.yd.mq.activemq.common.activemq.imp;

import com.yd.mq.activemq.common.activemq.AMQConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
@Service("AMQConsumerService")
public class AMQConsumerServiceImp implements AMQConsumerService {
    private final static Logger log = LoggerFactory.getLogger(AMQConsumerServiceImp.class);

    @Override
    public String receiveTextMessage(JmsTemplate jmsTemplate, Destination destination) throws JMSException {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        log.debug("\n" + Thread.currentThread().getStackTrace()[1].getMethodName() + "接受到:" + destination.toString() + "队列的信息："  + textMessage.getText());
        return textMessage.getText();
    }

    @Override
    public <T extends Serializable> T receiveMessage(JmsTemplate jmsTemplate, Destination destination) throws JMSException {
        ObjectMessage objectMessage = (ObjectMessage) jmsTemplate.receive(destination);
        log.debug("\n" + Thread.currentThread().getStackTrace()[1].getMethodName() + "接受到:" + destination.toString() + "队列的信息：" + objectMessage.getObject().toString());
        return (T) objectMessage.getObject();
    }

    @Override
    public <T extends Serializable> T receiveMessage(JmsTemplate jmsTemplate, Destination destination, Class<T> cls) throws JMSException {
        ObjectMessage objectMessage = (ObjectMessage) jmsTemplate.receive(destination);
        T obj = (T) objectMessage.getObject();
        log.debug("\n" + Thread.currentThread().getStackTrace()[1].getMethodName() + "接受到:" + destination.toString() + "队列的信息：" + obj.toString());
        return obj;
    }
}
