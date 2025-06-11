package ftesting;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

class ExampleRouteWithSupportTest extends CamelTestSupport {

    private static final String mockRouteFileOutput = "mock:routeFileOutput";

    private static final String directExampleRouteTest = "direct:exampleRouteTest";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        super.beforeTestExecution(context);

        // config for producer
        AdviceWith.adviceWith(
                context(),
                ExampleRoute.ROUTE_ID,
                route -> route
                        .interceptSendToEndpoint("file:src/main/resources/files/output/test")
                        .skipSendToOriginalEndpoint()
                        .to(mockRouteFileOutput)
        );

        // config for consumer
        AdviceWith.adviceWith(
          context(),
          ExampleRoute.ROUTE_ID,
          route -> route
                  .replaceFromWith(directExampleRouteTest)
        );

    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new ExampleRoute();
    }

    @Test
    void testExampleRoute() throws InterruptedException {
        String testBody = "Bye World";
        MockEndpoint toFileOutput = getMockEndpoint(mockRouteFileOutput);
        toFileOutput.expectedMessageCount(1);
        toFileOutput.message(0).body().isEqualTo(testBody.toUpperCase());

        // to call directly a consumer
        template().sendBody(directExampleRouteTest, testBody);

        toFileOutput.assertIsSatisfied();
    }

}