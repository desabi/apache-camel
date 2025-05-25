package messagerouting.contentbased.egb;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Content-Based Router Pattern Example using Apache Camel 4.x
 * 
 * This example demonstrates how to route messages to different endpoints
 * based on the content of the message using Camel's choice() DSL.
 */
public class ContentBasedRouterExample {
    
    public static void main(String[] args) throws Exception {
        // Create CamelContext - the runtime container for routes
        CamelContext camelContext = new DefaultCamelContext();
        
        // Add route definition to the context
        camelContext.addRoutes(new OrderProcessingRoute());
        
        // Start the Camel context
        camelContext.start();
        
        // Create a producer template to send messages
        ProducerTemplate producer = camelContext.createProducerTemplate();
        
        // Send test messages with different content
        System.out.println("=== Sending test orders ===");
        
        // Priority order - should go to priority queue
        producer.sendBody("direct:orders", 
            "<order><type>PRIORITY</type><customer>John Doe</customer><amount>1500</amount></order>");
        
        // Regular order with high amount - should go to manager approval
        producer.sendBody("direct:orders", 
            "<order><type>REGULAR</type><customer>Jane Smith</customer><amount>5500</amount></order>");
        
        // Regular order with low amount - should go to standard processing
        producer.sendBody("direct:orders", 
            "<order><type>REGULAR</type><customer>Bob Johnson</customer><amount>250</amount></order>");
        
        // Invalid order type - should go to error handling
        producer.sendBody("direct:orders", 
            "<order><type>INVALID</type><customer>Test User</customer><amount>100</amount></order>");
        
        // Wait a bit to see the processing
        Thread.sleep(2000);
        
        // Stop the context
        camelContext.stop();
    }
}