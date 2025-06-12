package com.desabi.guide.camel.spring.quarkus.routes;

import org.apache.camel.builder.RouteBuilder;

public class ExampleDirectRoute extends RouteBuilder {
    public static final String ID = "exampleDirectRoute";

    @Override
    public void configure() throws Exception {
        from("direct:example-direct-route")
                .routeId(ID)
                .log("Received request: ${body}");
    }
}
