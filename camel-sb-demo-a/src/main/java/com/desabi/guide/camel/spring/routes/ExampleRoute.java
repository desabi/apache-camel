package com.desabi.guide.camel.spring.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExampleRoute extends RouteBuilder {

    public static final String ID = "exampleRoute";

    @Override
    public void configure() throws Exception {
        from("artemis:{{example.queue.input.name}}")
                .routeId(ID)
                .log("Received request: ${body}")
                .bean("exampleMessageTransformer")
                .to("artemis:{{example.queue.output.name}}");
    }
}
