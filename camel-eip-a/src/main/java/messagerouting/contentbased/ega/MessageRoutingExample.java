package messagerouting.contentbased.ega;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Message Routing Pattern Example using Apache Camel 4.x
 * 
 * This example demonstrates content-based routing where orders are routed
 * to different processors based on order type and priority.
 */
public class MessageRoutingExample {
    
    public static void main(String[] args) throws Exception {
        // Create Camel context
        CamelContext context = new DefaultCamelContext();
        
        // Add route configuration
        context.addRoutes(new OrderRoutingRoute());
        
        // Start the context
        context.start();
        
        // Send test messages
        sendTestMessages(context);
        
        // Wait a bit to see the results
        Thread.sleep(2000);
        
        // Stop the context
        context.stop();
    }
    
    private static void sendTestMessages(CamelContext context) throws Exception {
        // Send different types of orders
        context.createProducerTemplate().sendBodyAndHeader(
            "direct:orderInput", 
            createOrder("ORD001", "PREMIUM", "ELECTRONICS", 1500.00),
            "orderType", "PREMIUM"
        );
        
        context.createProducerTemplate().sendBodyAndHeader(
            "direct:orderInput", 
            createOrder("ORD002", "STANDARD", "BOOKS", 25.99),
            "orderType", "STANDARD"
        );
        
        context.createProducerTemplate().sendBodyAndHeader(
            "direct:orderInput", 
            createOrder("ORD003", "EXPRESS", "CLOTHING", 89.50),
            "orderType", "EXPRESS"
        );
        
        context.createProducerTemplate().sendBodyAndHeader(
            "direct:orderInput", 
            createOrder("ORD004", "PREMIUM", "JEWELRY", 2500.00),
            "orderType", "PREMIUM"
        );
    }
    
    private static String createOrder(String orderId, String type, String category, double amount) {
        return String.format(
            "{\"orderId\":\"%s\",\"type\":\"%s\",\"category\":\"%s\",\"amount\":%.2f}",
            orderId, type, category, amount
        );
    }
}