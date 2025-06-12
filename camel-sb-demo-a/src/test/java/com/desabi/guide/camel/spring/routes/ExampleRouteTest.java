package com.desabi.guide.camel.spring.routes;

import com.desabi.guide.camel.spring.bean.ExampleMessageTransformer;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@CamelSpringBootTest
@SpringBootTest
class ExampleRouteTest {

    @Autowired
    private CamelContext camelContext;

    @MockitoBean
    private ExampleMessageTransformer transformerMock;

    private final String directTestEndpoint = "direct:example-route-test";

    // inject mock endpoint
    @EndpointInject("mock:example-route-test")
    private MockEndpoint mockEndpoint;

    @Produce(directTestEndpoint)
    private ProducerTemplate producerTemplate;

    @BeforeEach
    public void replaceEndpoint() throws Exception {
        AdviceWith.adviceWith(
                camelContext,
                ExampleRoute.ID,
                route -> route
                        .interceptSendToEndpoint("jms:{{example.queue.output.name}}")
                        .skipSendToOriginalEndpoint()
                        .to(mockEndpoint)
        );

        AdviceWith.adviceWith(
                camelContext,
                ExampleRoute.ID,
                route -> route.replaceFromWith(directTestEndpoint)
        );
    }

    @Test
    void testExampleRoute() throws InterruptedException {
        String testBody = "test-body";
        String testHeader = "test-header";
        mockEndpoint.expectedMessageCount(1);

        producerTemplate.sendBodyAndHeader(testBody, "example-header", testHeader);

        Mockito.verify(transformerMock, Mockito.times(1)).transform(Mockito.any());

        mockEndpoint.assertIsSatisfied();
    }
}