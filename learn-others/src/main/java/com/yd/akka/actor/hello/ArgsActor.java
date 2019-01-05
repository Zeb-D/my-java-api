package com.yd.akka.actor.hello;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Yd on  2018-02-07
 * @description
 **/
public class ArgsActor extends AbstractActor {
    private String name;

    public ArgsActor(String nmae) {
        this.name = nmae;
        System.out.println("actor :" + name);
        receive(ReceiveBuilder
                .match(String.class, message -> {
                    System.out.println("Receive message : " + message);
                })
                .matchAny(o -> System.out.println("received unknown message: " + o))
                .build()
        );
    }

}
