package com.desabisc.guide.camel.processeg.egb;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MessageTranslatorRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Define a route that reads from a file, processes it through our translator, and writes to another file
        from("file:src/data/input?noop=true")
            .log("Received message: ${body}")
            .process(new UppercaseTranslator())
            .log("Translated message: ${body}")
            .to("file:src/data/output");
    }
}