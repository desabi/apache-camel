package https.ega;

// Main Application Class
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;


public class CamelHttpExample {
    
    public static void main(String[] args) throws Exception {
        // Create Camel Context
        try (CamelContext camelContext = new DefaultCamelContext()) {

            // Add route to the context
            camelContext.addRoutes(new UserApiRoute());

            // Start the context
            camelContext.start();

            System.out.println("Camel Context started. Fetching users from GoRest API...");

            // Keep the application running for demonstration
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));

            // Stop the context
            camelContext.stop();
        }
        System.out.println("Camel Context stopped.");
    }
}