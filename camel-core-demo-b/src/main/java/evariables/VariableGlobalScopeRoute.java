package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableGlobalScopeRoute extends RouteBuilder {

    private final static String ROUTE_ID = "variableGlobalScopeRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .routeId(ROUTE_ID)
                .log("Value of variable variable.global.myVariable is: ${variable.global:myVariable}");
    }
}
