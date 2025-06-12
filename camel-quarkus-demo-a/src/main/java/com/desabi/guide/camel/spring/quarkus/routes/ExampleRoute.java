package com.desabi.guide.camel.spring.quarkus.routes;

import org.apache.camel.builder.RouteBuilder;

public class ExampleRoute extends RouteBuilder {

    public static final String ID = "ExampleRoute";

    @Override
    public void configure() throws Exception {
        from("jms:{{example.queue.input.name}}") // jms -> artemis
                .routeId(ID)
                .log("Received request: ${body}")
                .bean("exampleMessageTransformer")
                .to("jms:{{example.queue.output.name}}"); // jms -> rabbitmq
    }
}
