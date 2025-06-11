package ftesting;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ExampleRouteTest {

    private CamelContext camelContext;

    private static final String mockRouteFileOutput = "mock:routeFileOutput";

    private static final String directExampleRouteTest = "direct:exampleRouteTest";

    @BeforeEach
    public void setUp() throws Exception {
        camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new ExampleRoute());

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

        camelContext.start();
    }

    @AfterEach
    public void tearDown() {
        camelContext.stop();
    }

    @Test
    void testExampleRoute() throws InterruptedException {
        String testBody = "Bye World";
        MockEndpoint toFileOutput = camelContext.getEndpoint(mockRouteFileOutput, MockEndpoint.class);
        toFileOutput.expectedMessageCount(1);
        toFileOutput.message(0).body().isEqualTo(testBody.toUpperCase());

        // to call directly a consumer
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody(directExampleRouteTest, testBody);

        toFileOutput.assertIsSatisfied();
    }

}