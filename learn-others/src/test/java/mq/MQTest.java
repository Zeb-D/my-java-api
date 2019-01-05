package mq;

import com.yd.mq.activemq.common.activemq.AMQConsumerService;
import com.yd.mq.activemq.common.activemq.AMQProducerService;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Random;

/**
 * @author Yd on  2017-12-14
 * @Description：
 **/
public class MQTest extends BaseJunitTest {

    @Resource(name = "AMQProducerService")
    private AMQProducerService producerService;
    @Resource(name = "AMQConsumerService")
    private AMQConsumerService consumerService;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;
    @Resource(name = "goodsInfoQueue")
    private Destination destination;

    @Test
    public void testSendMsg() {
        producerService.sendMessage(jmsTemplate,destination, "My name is mq" + (new Random()).nextInt());
    }

    @Test
    public void testSendObject() {
        User user = new User();
        user.setId(1234);
        user.setName("mq");
        producerService.sendMessage(jmsTemplate,destination, user);
    }

    @Test
    public void testRecive() throws JMSException {
        Object obj = consumerService.receiveMessage(jmsTemplate,destination);
        System.out.println("\n##" + obj);
    }

    @Test
    public void testStringMsg() throws JMSException {
//        jmsTemplate.setReceiveTimeout(10000);
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        System.out.println("\n##" + textMessage.getText());

    }

}
