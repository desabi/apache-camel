package com.desabi.guide.camel.spring.quarkus.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

@ApplicationScoped
@Named("exampleMessageTransformer")
public class ExampleMessageTransformer {

    private final ExampleMessageBodyMapper bodyMapper;
    private final ExampleMessageHeader headerMapper;

    public ExampleMessageTransformer(ExampleMessageBodyMapper bodyMapper, ExampleMessageHeader headerMapper) {
        this.bodyMapper = bodyMapper;
        this.headerMapper = headerMapper;
    }

    public void transform(Exchange exchange) {
        Message msg = exchange.getMessage();
        String header = "example-header";
        msg.setBody(bodyMapper.map(msg.getBody(String.class)));
        msg.setHeader(header, headerMapper.map(msg.getHeader(header, String.class)));
    }
}
