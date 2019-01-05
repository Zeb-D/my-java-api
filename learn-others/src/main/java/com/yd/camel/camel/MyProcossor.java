package com.yd.camel.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Date;

public class MyProcossor implements Processor {
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody(new Date().toString());
    }
}