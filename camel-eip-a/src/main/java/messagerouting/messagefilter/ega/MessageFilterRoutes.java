package messagerouting.messagefilter.ega;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.builder.PredicateBuilder.and;
import static org.apache.camel.builder.PredicateBuilder.or;

/**
 * RouteBuilder class that defines all the message filtering routes
 * Each route demonstrates a different aspect of the Message Filter pattern
 */
public class MessageFilterRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // ==========================================
        // Route 1: Content-Based Message Filtering
        // ==========================================
        // This route demonstrates filtering messages based on their content
        /*from("timer://contentFilter?period=5000&repeatCount=3")
            .routeId("content-based-filter")
            .description("Demonstrates content-based message filtering")
            // Generate different types of messages for testing
            .process(new MessageGenerator())
            .log("Generated message: ${body}")
            // Apply content-based filter - only process messages containing "IMPORTANT"
            .filter(body().contains("IMPORTANT"))
                .log("✅ Content Filter PASSED: ${body}")
                .to("direct:processImportantMessage")
            .end() // End of filter block
            .log("Message processing completed for content filter");*/

        // ==========================================
        // Route 2: Header-Based Message Filtering
        // ==========================================
        // This route demonstrates filtering messages based on headers
        /*from("timer://headerFilter?period=6000&repeatCount=8")
            .routeId("header-based-filter")
            .description("Demonstrates header-based message filtering")
            // Set up test headers
            .process(new HeaderGenerator())
            .log("Message with headers - Priority: ${header.Priority}, Type: ${header.MessageType}")
            // Filter based on header values - only high priority messages
            .filter(header("Priority").isEqualTo("HIGH"))
                .log("✅ Header Filter PASSED: Priority=${header.Priority}, Body=${body}")
                .to("direct:processHighPriorityMessage")
            .end()
            .log("Header-based filtering completed");*/

        // ==========================================
        // Route 3: Custom Predicate Filtering
        // ==========================================
        // This route demonstrates complex filtering using custom predicates
        /*from("timer://customFilter?period=7000&repeatCount=7")
            .routeId("custom-predicate-filter")
            .description("Demonstrates custom predicate message filtering")
            .process(new OrderMessageGenerator())
            .log("Order message: ${body}")
            // Complex filter: messages with specific patterns and header conditions
            .filter(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                String region = exchange.getIn().getHeader("Region", String.class);
                Double amount = exchange.getIn().getHeader("Amount", Double.class);

                System.out.println("[Region= " + region + ", amount = " + amount + "]");

                // Custom business logic: Process orders from specific regions with high amounts
                return body != null && body.startsWith("ORDER") &&
                        "NORTH_AMERICA".equals(region) &&
                         amount != null && amount > 300.0;
                })
                .log("✅ Custom Filter PASSED: ${body} (Region: ${header.Region}, Amount: ${header.Amount})")
                .to("direct:processLargeOrder")
                .end()
                .log("Custom predicate filtering completed");*/

        // ==========================================
        // Route 4: Method-Based Filtering
        // ==========================================
        // This route demonstrates filtering using bean methods
        /*from("timer://methodFilter?period=8000&repeatCount=6")
            .routeId("method-based-filter")
            .description("Demonstrates method-based message filtering")
            .process(new ProductMessageGenerator())
            .log("Product message: ${body}")
            // Use a bean method to determine if message should be processed
            .filter().method(MessageFilterRoutes.class, "isValidProduct")
                .log("✅ Method Filter PASSED: ${body}")
                .to("direct:processValidProduct")
            .end()
            .log("Method-based filtering completed");*/
        // ==========================================
        // Route 5: Multiple Filters with Logical Operators
        // ==========================================
        // This route demonstrates combining multiple filter conditions
        from("timer://multipleFilters?period=9000&repeatCount=5")
            .routeId("multiple-filters")
            .description("Demonstrates multiple filter conditions")
            .process(new ComplexMessageGenerator())
            .log("Complex message: ${body}")
            // Multiple filter conditions using AND logic
            .filter(and(body().contains("URGENT"), (header("Department").isEqualTo("SALES"))))
                .log("✅ Multiple Filters PASSED: ${body}")
                .to("direct:processUrgentSalesMessage")
            .end()
            // Alternative filter using OR logic
            .filter(or(header("VIP").isEqualTo(true), (header("Amount").isGreaterThan(5000))))
                .log("✅ VIP/High Amount Filter PASSED: ${body}")
                .to("direct:processVIPMessage")
            .end()
            .log("Multiple filter processing completed");

        // ==========================================
        // Processing Routes (Destinations)
        // ==========================================
        // These routes represent the downstream processing after filtering

        from("direct:processImportantMessage")
            .routeId("process-important")
            .log("🔄 Processing important message: ${body}")
            .process(exchange -> {
                // Simulate important message processing
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("PROCESSED: " + body);
            })
            .log("✨ Important message processed successfully");

        from("direct:processHighPriorityMessage")
            .routeId("process-high-priority")
            .log("🔄 Processing high priority message: ${body}")
            .delay(1000) // Simulate processing time
            .log("✨ High priority message processed with special handling");

        from("direct:processLargeOrder")
            .routeId("process-large-order")
            .log("🔄 Processing large order: ${body}")
            .process(exchange -> {
                // Simulate order processing with additional validation
                Double amount = exchange.getIn().getHeader("Amount", Double.class);
                exchange.getIn().setHeader("ProcessedAmount", amount * 1.1); // Add processing fee
            })
            .log("✨ Large order processed - Final amount: ${header.ProcessedAmount}");

        from("direct:processValidProduct")
            .routeId("process-valid-product")
            .log("🔄 Processing valid product: ${body}")
            .log("✨ Product validation and processing completed");

        from("direct:processUrgentSalesMessage")
            .routeId("process-urgent-sales")
            .log("🔄 Processing urgent sales message: ${body}")
            .log("✨ Urgent sales message escalated and processed");

        from("direct:processVIPMessage")
             .routeId("process-vip")
             .log("🔄 Processing VIP message: ${body}")
             .log("✨ VIP message processed with premium service");
    }

    /**
     * Bean method for method-based filtering example
     * This method determines if a product message should be processed
     *
     * @param exchange The Camel exchange containing the message
     * @return true if the product should be processed, false otherwise
     */
    public static boolean isValidProduct(Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        String category = exchange.getIn().getHeader("Category", String.class);
        Boolean inStock = exchange.getIn().getHeader("InStock", Boolean.class);

        System.out.println("[category = " + category + ", inStock = " + inStock + "]");

        // Business logic: Only process electronics that are in stock
        return body != null &&
                body.contains("PRODUCT") &&
                "ELECTRONICS".equals(category) &&
                Boolean.TRUE.equals(inStock);
    }
}
