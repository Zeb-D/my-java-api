package com.yd.mq.activemq.common.activemq.imp;

import com.yd.mq.activemq.common.activemq.AMQProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
@Service("AMQProducerService")
public class AMQProducerServiceImp implements AMQProducerService {
    private final static Logger log = LoggerFactory.getLogger(AMQProducerServiceImp.class);

    @Override
    public void sendMessage(JmsTemplate jmsTemplate, Destination destination, final Serializable message) {
        log.debug("\nInfo信息：" + getClass() + "-" + Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + message);
        
        jmsTemplate.convertAndSend(destination, message);
    }
}
