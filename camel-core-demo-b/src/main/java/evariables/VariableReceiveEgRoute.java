package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableReceiveEgRoute extends RouteBuilder {

    private final static String ROUTE_ID = "variableReceiveExampleRoute";

    public static final String SERVICE_ROUTE_ID = "variableReceiveServiceExampleRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .routeId(ROUTE_ID)
                // this is override by the setBody in the service e.g. route
                .setBody(constant("Initial Request Body"))
                // to avoid the override
                .setProperty("request", simple("${body}"))
                .to("direct:variable-receive-service-eg")
                //.toV("direct:variable-receive-service-eg", null, "responseFromService")
                .log("Body: ${body}; property: ${exchangeProperty.request} , variable: ${variable.responseFromService}");

        from("direct:variable-receive-service-eg")
                .routeId(SERVICE_ROUTE_ID)
                .setBody(constant("Response from Service"));
    }
}
