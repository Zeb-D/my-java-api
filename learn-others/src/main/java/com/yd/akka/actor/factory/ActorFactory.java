package com.yd.akka.actor.factory;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author Yd on  2018-02-07
 * @description
 **/
public class ActorFactory {

    public static Props props(Class<? extends AbstractActor> clazz, String response) {
        return Props.create(clazz, response);
    }
}
