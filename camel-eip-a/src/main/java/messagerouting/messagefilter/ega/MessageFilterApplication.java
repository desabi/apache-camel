package messagerouting.messagefilter.ega;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Apache Camel Message Filter Pattern Demonstration
 *
 * This application demonstrates the Enterprise Integration Pattern "Message Filter"
 * using Apache Camel 4.8 and Java 21. The Message Filter pattern allows selective
 * processing of messages based on specific criteria, letting desired messages pass
 * through while discarding unwanted ones.
 *
 * Key concepts demonstrated:
 * 1. Content-based filtering using message body
 * 2. Header-based filtering using message headers
 * 3. Custom predicate filtering with complex logic
 * 4. Method-based filtering using bean methods
 * 5. Multiple filter conditions with logical operators
 */
public class MessageFilterApplication {

    private static final Logger logger = LoggerFactory.getLogger(MessageFilterApplication.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting Apache Camel Message Filter Pattern Demo");

        // Create a new CamelContext - the runtime container for Camel routes
        // Using DefaultCamelContext for standalone applications (no Spring needed)
        CamelContext camelContext = new DefaultCamelContext();

        try {
            // Add route configurations to the context
            camelContext.addRoutes(new MessageFilterRoutes());

            // Start the Camel context - this begins message processing
            camelContext.start();
            logger.info("Camel Context started successfully");

            // Keep the application running for demonstration purposes
            // In a real application, this might be controlled by external events
            logger.info("Application is running. Press Ctrl+C to stop.");

            // Run for 60 seconds to see multiple message cycles
            Thread.sleep(TimeUnit.SECONDS.toMillis(60));

        } catch (Exception e) {
            logger.error("Error occurred during application execution", e);
        } finally {
            // Properly shutdown the Camel context
            camelContext.stop();
            logger.info("Camel Context stopped. Application terminated.");
        }
    }
}