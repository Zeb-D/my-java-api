package com.yd.akka.actor.hello;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class JavaPongActor extends AbstractActor {

    @Override
    public PartialFunction receive() {
        return ReceiveBuilder
                .matchEquals("Ping", s ->
                        sender().tell("Pong", ActorRef.noSender()))
                .match(String.class, s -> System.out.println("It's a string: " + s))
                .matchAny(x ->
                        sender().tell(
                                new Status.Failure(new Exception("unknown message")), self()
                        ))
                .build();
    }
}