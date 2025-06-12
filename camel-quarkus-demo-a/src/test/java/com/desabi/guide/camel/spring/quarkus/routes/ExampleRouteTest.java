package com.desabi.guide.camel.spring.quarkus.routes;

import com.desabi.guide.camel.spring.quarkus.bean.ExampleMessageTransformer;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ExampleRouteTest {

    @Inject
    CamelContext camelContext;

    @InjectMock
    ExampleMessageTransformer transformerMock;

    private final String directTestEndpoint = "direct:example-route-test";

    @EndpointInject("mock:example-route-test")
    MockEndpoint mock;

    @Produce(directTestEndpoint)
    ProducerTemplate template;

    @BeforeEach
    public void replaceEndpoint() throws Exception {
        AdviceWith.adviceWith(
            camelContext,
            ExampleRoute.ID,
                route -> route
                        .interceptSendToEndpoint("jms:{{example.queue.output.name}}")
                        .skipSendToOriginalEndpoint()
                        .to(mock)
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
        mock.expectedMessageCount(1);

        template.sendBodyAndHeader(testBody, "example-header", testHeader);

        Mockito.verify(transformerMock, Mockito.times(1)).transform(Mockito.any());

        mock.assertIsSatisfied();
    }

}