package com.yd.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.Executors;

/**
 * @author Yd on  2018-04-03
 * @description
 **/
public class ChoiceCamel {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:src/data?noop=true")
                .filter(xpath("/order[not(@test)]"))
                .to("jms:incomingOrders");
                from("jms:incomingOrders")
                .choice()
                .when(header("CamelFileName").endsWith(".xml"))
                .to("jms:xmlOrders")
                .when(header("CamelFileName").endsWith(".csv"))
                .to("jms:csvOrders")
                .when(header("CamelFileName").regex("^.*(csv|csl)$"))
                .to("jms:csvOrders")
                .otherwise()
                .to("jms:badOrders")
                .end()
                .to("jms:continuedProcessing");

                from("jms:xmlOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received XML order: "
                                + exchange.getIn().getHeader("CamelFileName"));
                    }
                });
                from("jms:csvOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received CSV order: "
                                + exchange.getIn().getHeader("CamelFileName"));
                    }
                });
            }
        });
    }

    public static void multicast() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jms:xmlOrders")
                .multicast().parallelProcessing().stopOnException()
                .executorService(Executors.newFixedThreadPool(16))
                .to("jms:accounting", "jms:production");
            }
        });

    }
}
