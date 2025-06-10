package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableRouteScopeRoute extends RouteBuilder {

    public static final String FIRST_ROUTE_ID = "variableScopeRoute1";

    public static final String SECOND_ROUTE_ID = "variableScopeRoute2";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .id(FIRST_ROUTE_ID)
                // sets variable with a route scope
                .setVariable("route:myVariable", constant("My Variable Value"))
                // sends an exchange to the second route
                .to("direct:variable-scope-route2");

        from("direct:variable-scope-route2")
                .id(SECOND_ROUTE_ID)
                .log(
                        "Value of variable.route:" + FIRST_ROUTE_ID + ":myVariables is: " +
                        "${variable.route:" + FIRST_ROUTE_ID + ":myVariable}"
                )
                // this will be null because the variable has exchange scope, and the other variable has route scope
                .log("Value of variable.myVariable is: ${variable.myVariable}");
    }
}
