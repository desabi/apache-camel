package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableSendRouteEg extends RouteBuilder {

    public static final String ROUTE_ID = "variableSendExampleRoute";
    public static final String SERVICE_ROUTE_ID = "variableSendServiceRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .routeId(ROUTE_ID)

                // we want this body not to be overridden by the service route
                .setBody(constant("Initial Request Body"))
                // this sends the body "Initial Request Body to the external service, we do not want this
                // we want to send another body request to the external service
                //.to("direct:variable-send-service-eg")
                .setVariable("requestToService", constant("My Request to Service Route"))
                .toV(
                        "direct:variable-send-service-eg",
                        "requestToService",
                        "responseFromService"
                )
                .log("Body: ${body}, variable: ${variable.responseFromService}");

        // query external service
        // send some request payload
        from("direct:variable-send-service-eg")
                .routeId(SERVICE_ROUTE_ID)
                .log("Service received request: ${body}")
                .setBody(constant("Response From Service"));

    }
}
