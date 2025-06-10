package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableRouteEg extends RouteBuilder {

    public static final String ROUTE_ID = "variableExampleRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .id(ROUTE_ID)
                // handle variables
                .setVariable("myVariable", constant("My Variable Value"))
                .process(exchange -> {
                    exchange.getVariable("myVariable", String.class);
                    exchange.setVariable("myVariable2", "My Variable Value 2");
                })
                .transform(simple("Transformed body: ${variable.myVariable}"))
                .log("${body}");
    }
}
