package rest.ega;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.undertow.UndertowComponent;

/**
 * Apache Camel REST Component Example
 * Java 21, Camel 4.8
 */
public class CamelRestApplication {
    public static void main(String[] args) throws Exception {
        // Create CamelContext
        CamelContext camelContext = new DefaultCamelContext();

        // Create UserService instance
        UserService userService = new UserService();

        // Add Undertow component for HTTP server
        camelContext.addComponent("undertow", new UndertowComponent());

        // Create and add route builder with UserService dependency
        RestRouteBuilder routeBuilder = new RestRouteBuilder(userService);
        camelContext.addRoutes(routeBuilder);

        // Start the context
        camelContext.start();

        System.out.println("Camel REST API started!");
        System.out.println("Available endpoints:");
        System.out.println("GET    http://localhost:8080/api/users");
        System.out.println("GET    http://localhost:8080/api/users/{id}");
        System.out.println("POST   http://localhost:8080/api/users");
        System.out.println("PUT    http://localhost:8080/api/users/{id}");
        System.out.println("DELETE http://localhost:8080/api/users/{id}");
        System.out.println("\nPress Enter to stop...");

        // Keep the application running
        System.in.read();

        // Stop the context
        camelContext.stop();
    }
}