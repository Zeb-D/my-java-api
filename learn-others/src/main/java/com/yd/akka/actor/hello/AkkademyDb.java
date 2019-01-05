package com.yd.akka.actor.hello;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.yd.akka.entity.SetRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yd on  2018-02-03
 * @description 使用Java实现的Actor收到消息后的响应
 **/
public class AkkademyDb extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    public final Map<String, Object> map = new HashMap<String, Object>();

    private AkkademyDb() {
        System.out.println("AkkademyDb");
        receive(ReceiveBuilder
                .match(SetRequest.class, message -> {
                    log.info("Received Set request: {}", message);
                    map.put(message.getKey(), message.getValue());
                })
                .matchAny(o -> log.info("received unknown message: {}", o))
                .build()
        );
    }
}
