package com.yd.mq.activemq.common.util;

import com.yd.mq.activemq.common.CallBack;
import com.yd.mq.activemq.common.activemq.AMQConsumerService;
import com.yd.mq.activemq.common.activemq.AMQProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;
import java.util.List;

/**
 * @author Yd on  2018-01-16
 * @Description：
 **/
public class AMQUtil {
    private static final Logger log = LoggerFactory.getLogger(AMQUtil.class);
    private static AMQUtil amqUtil;
    @Resource(name = "AMQConsumerService")
    private AMQConsumerService consumerService;
    @Resource(name = "AMQProducerService")
    private AMQProducerService producerService;

    public static void sendMessage(JmsTemplate jmsTemplate, Destination destination, final Serializable message) {
        Long startTime = System.currentTimeMillis();
        amqUtil.producerService.sendMessage(jmsTemplate, destination, message);
        log.info("sendMessage cost time :" + (System.currentTimeMillis() - startTime));
    }

    public static <T extends Serializable> void sendMessage(JmsTemplate jmsTemplate, Destination destination, final List<T> messages) {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < messages.size(); i++) {
            amqUtil.producerService.sendMessage(jmsTemplate, destination, messages.get(i));
        }
        log.info("sendMessage cost time :" + (System.currentTimeMillis() - startTime));
    }

    public static <T extends Serializable> void sendMessage(JmsTemplate jmsTemplate, Destination destination, CallBack callBack) {
        Long startTime = System.currentTimeMillis();
        List a = callBack.getMessage(4);
        for (int i = 0; i < a.size(); i++) {
            amqUtil.producerService.sendMessage(jmsTemplate, destination, (Serializable) a.get(i));
        }
        log.info("sendMessage cost time :" + (System.currentTimeMillis() - startTime));
    }


    @PostConstruct
    public void init() {
        amqUtil = this;
        amqUtil.consumerService = this.consumerService;
        amqUtil.producerService = this.producerService;
    }

    static class AMQSendThread implements Runnable {

        private JmsTemplate jmsTemplate;
        private Destination destination;
        private Serializable message;

        private AMQSendThread(JmsTemplate jmsTemplate, Destination destination, Serializable message) {
            this.jmsTemplate = jmsTemplate;
            this.destination = destination;
            this.message = message;
        }

        public static AMQUtil.AMQSendThread insertLog(JmsTemplate jmsTemplate, Destination destination, Serializable message) {
            return new AMQUtil.AMQSendThread(jmsTemplate, destination, message);
        }

        @Override
        public void run() {
            log.info("send AMQ message:" + message);
            amqUtil.producerService.sendMessage(jmsTemplate, destination, message);
        }
    }

}
