package com.desabisc.camel.resource;

import com.desabisc.camel.dto.Order;
import com.desabisc.camel.processor.OrderProcessor;
import com.desabisc.camel.service.OrderService;
import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ApplicationResource extends RouteBuilder {

    @Autowired
    private OrderService orderService;

    @BeanInject
    private OrderProcessor orderProcessor;

    @Override
    public void configure() throws Exception {

        // build camel component
        restConfiguration()
                .component("servlet")
                .port(9090)
                .host("localhost")
                .bindingMode(RestBindingMode.json);


        // expose rest api
        rest()
                .get("/hello-world")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .setBody(constant("Welcome to Apache Camel"))
                        .endRest();

        // enable camel servlet in properties file

        rest()
                .get("/getOrders")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .setBody(constant(orderService.getOrder()))
                .endRest();

        rest()
                .post("/addOrder")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Order.class)
                .outType(Order.class)
                .route()
                .process(orderProcessor)
                .endRest();

    }
}
