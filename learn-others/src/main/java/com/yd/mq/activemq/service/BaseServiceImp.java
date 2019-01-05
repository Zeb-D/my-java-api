package com.yd.mq.activemq.service;

import com.yd.mq.activemq.common.CallBack;
import com.yd.mq.activemq.common.util.AMQUtil;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yd on  2018-01-16
 * @Description：
 **/
@Service("baseService")
public class BaseServiceImp implements BaseService, CallBack {
    @Resource(name = "jmsQueueTemplate")
    JmsTemplate jmsTemplate;
    @Resource(name = "goodsOrgQueueDestination")
    Destination goodsOrgQueueDestination;
    private Random random = new Random(100);

    @Override
    public List<? extends Serializable> getMessage(Integer length) {
        List<String> a = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            a.add(String.valueOf(random.nextDouble()));
        }
        return a;
    }

    @Override
    public void sendMessage() {
        AMQUtil.sendMessage(jmsTemplate, goodsOrgQueueDestination, this);
    }
}
