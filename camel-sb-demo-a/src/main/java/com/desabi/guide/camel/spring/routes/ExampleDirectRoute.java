package com.desabi.guide.camel.spring.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExampleDirectRoute extends RouteBuilder {

    public static final String ID = "exampleDirectRoute";

    @Override
    public void configure() throws Exception {
        from("direct:example-direct-route")
                .routeId(ID)
                .log("Received request: ${body}");
    }
}
