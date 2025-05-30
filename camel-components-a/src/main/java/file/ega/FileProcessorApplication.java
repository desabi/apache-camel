package file.ega;// Main Application Class

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class FileProcessorApplication {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessorApplication.class);

    public static void main(String[] args) {
        CamelContext camelContext = new DefaultCamelContext();

        try {
            // Add the route to the context
            camelContext.addRoutes(new FileProcessingRoute());

            // Start the context
            camelContext.start();
            logger.info("Camel Context started successfully");

            // Keep the application running
            Thread.sleep(TimeUnit.SECONDS.toMillis(30)); // Run for 30 seconds
        } catch (Exception e) {
            logger.error("Error running Camel application", e);
        } finally {
            // Properly shutdown the Camel context
            camelContext.stop();
            logger.info("Camel Context stopped. Application terminated.");
        }
    }
}