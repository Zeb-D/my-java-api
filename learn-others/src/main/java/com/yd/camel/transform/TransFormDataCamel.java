package com.yd.camel.transform;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * @author Yd on  2018-04-03
 * @description
 **/
public class TransFormDataCamel {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("quartz://report?cron=0+0+6+*+*+?")
                        .to("http://riders.com/orders/cmd=received&date=yesterday")
                        .bean(new OrderToCsvBean())
                        .to("file://riders/orders?fileName=report-${header.Date}.csv");
            }
        });
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .transform(new Expression() {
                            public <T> T evaluate(Exchange exchange, Class<T> type) {
                                String body = exchange.getIn().getBody(String.class);
                                body = body.replaceAll("\n", "<br/>");
                                body = "<body>" + body + "</body>";
                                return (T) body;
                            }
                        })
                        .to("mock:result");
            }
        });

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("quartz://report?cron=0+0+6+*+*+?")
                        .to("http://riders.com/orders/cmd=received")
                        .process(new OrderToCsvProcessor())
                        .pollEnrich("ftp://riders.com/orders/?username=rider&password=secret",
                                new AggregationStrategy() {
                                    public Exchange aggregate(Exchange oldExchange,
                                                              Exchange newExchange) {
                                        if (newExchange == null) {
                                            return oldExchange;
                                        }
                                        String http = oldExchange.getIn()
                                                .getBody(String.class);
                                        String ftp = newExchange.getIn()
                                                .getBody(String.class);
                                        String body = http + "\n" + ftp;
                                        oldExchange.getIn().setBody(body);
                                        return oldExchange;
                                    }
                                })
                        .to("file://riders/orders");
            }
        });



    }

}
