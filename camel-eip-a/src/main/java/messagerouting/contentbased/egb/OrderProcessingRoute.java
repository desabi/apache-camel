package messagerouting.contentbased.egb;

import org.apache.camel.builder.RouteBuilder;

/**
 * Route definition implementing Content-Based Router pattern
 */
class OrderProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Main route with Content-Based Router
        from("direct:orders")
            .routeId("order-router")
            .log("Received order: ${body}")

            // Content-Based Router using choice() - this is the core pattern
            .choice()
                // Route 1: Priority orders go to priority processing
                .when(xpath("//order/type[text()='PRIORITY']"))
                    .log("Routing PRIORITY order to priority queue")
                    .to("direct:priority-processing")

                // Route 2: Regular orders with amount > 5000 need manager approval
                .when(xpath("//order/type[text()='REGULAR'] and //order/amount[text() > 5000]"))
                    .log("Routing high-value REGULAR order to manager approval")
                    .to("direct:manager-approval")

                // Route 3: Regular orders with amount <= 5000 go to standard processing
                .when(xpath("//order/type[text()='REGULAR'] and //order/amount[text() <= 5000]"))
                    .log("Routing standard REGULAR order to standard processing")
                    .to("direct:standard-processing")

                // Default route: Handle unknown/invalid order types
                .otherwise()
                    .log("Unknown order type - routing to error handler")
                    .to("direct:error-handling")
            .end(); // End of choice block

        // Priority processing endpoint
        from("direct:priority-processing")
                .routeId("priority-processor")
                .log("=== PRIORITY PROCESSING ===")
                .transform(simple("Priority order processed for customer: ${xpath(//order/customer/text())}"))
                .log("Result: ${body}");

        // Manager approval endpoint
        from("direct:manager-approval")
                .routeId("manager-approval")
                .log("=== MANAGER APPROVAL REQUIRED ===")
                .transform(simple("High-value order ($${xpath(//order/amount/text())}) sent for manager approval: ${xpath(//order/customer/text())}"))
                .log("Result: ${body}");

        // Standard processing endpoint
        from("direct:standard-processing")
                .routeId("standard-processor")
                .log("=== STANDARD PROCESSING ===")
                .transform(simple("Standard order processed for customer: ${xpath(//order/customer/text())} - Amount: $${xpath(//order/amount/text())}"))
                .log("Result: ${body}");

        // Error handling endpoint
        from("direct:error-handling")
                .routeId("error-handler")
                .log("=== ERROR HANDLING ===")
                .transform(constant("Invalid order format - please check order structure"))
                .log("Error: ${body}");
    }
}