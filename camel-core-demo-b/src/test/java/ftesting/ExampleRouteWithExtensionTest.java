package ftesting;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.infra.core.CamelContextExtension;
import org.apache.camel.test.infra.core.DefaultCamelContextExtension;
import org.apache.camel.test.infra.core.annotations.ContextFixture;
import org.apache.camel.test.infra.core.annotations.RouteFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class ExampleRouteWithExtensionTest {

    @RegisterExtension
    protected static CamelContextExtension camelContextExtension = new DefaultCamelContextExtension();

    private static final String mockRouteFileOutput = "mock:routeFileOutput";

    private static final String directExampleRouteTest = "direct:exampleRouteTest";

    @ContextFixture
    void setUp(CamelContext camelContext) throws Exception {

        // config for producer
        AdviceWith.adviceWith(
                camelContext,
                ExampleRoute.ROUTE_ID,
                route -> route
                        .interceptSendToEndpoint("file:src/main/resources/files/output/test")
                        .skipSendToOriginalEndpoint()
                        .to(mockRouteFileOutput)
        );

        // config for consumer
        AdviceWith.adviceWith(
          camelContext,
          ExampleRoute.ROUTE_ID,
          route -> route
                  .replaceFromWith(directExampleRouteTest)
        );
    }

    @RouteFixture
    void addRoute(CamelContext camelContext) throws Exception {
        camelContext.addRoutes(new ExampleRoute());
    }

    @Test
    void testExampleRoute() throws InterruptedException {
        String testBody = "Bye World";
        MockEndpoint toFileOutput = camelContextExtension.getMockEndpoint(mockRouteFileOutput);
        toFileOutput.expectedMessageCount(1);
        toFileOutput.message(0).body().isEqualTo(testBody.toUpperCase());

        // to call directly a consumer
        ProducerTemplate producerTemplate = camelContextExtension.getProducerTemplate();
        producerTemplate.sendBody(directExampleRouteTest, testBody);

        toFileOutput.assertIsSatisfied();
    }

}