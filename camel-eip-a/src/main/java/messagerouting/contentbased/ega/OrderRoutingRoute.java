package messagerouting.contentbased.ega;

import org.apache.camel.builder.RouteBuilder;

/**
 * Route Builder that implements the Message Routing Pattern
 */
class OrderRoutingRoute extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
        
        // Main routing logic - Content-Based Router
        from("direct:orderInput")
            .routeId("orderRouter")
            .log("Received order: ${body}")
            .choice()
                // Route premium orders (high value) to special processing
                .when(header("orderType").isEqualTo("PREMIUM"))
                    .log("Routing PREMIUM order to VIP processing")
                    .to("direct:premiumOrderProcessing")
                // Route express orders to expedited processing
                .when(header("orderType").isEqualTo("EXPRESS"))
                    .log("Routing EXPRESS order to expedited processing")
                    .to("direct:expressOrderProcessing")
                // Route standard orders to normal processing
                .when(header("orderType").isEqualTo("STANDARD"))
                    .log("Routing STANDARD order to normal processing")
                    .to("direct:standardOrderProcessing")
                // Default route for unknown order types
                .otherwise()
                    .log("Unknown order type, routing to manual review")
                    .to("direct:manualReview");
        
        // Premium order processing route
        from("direct:premiumOrderProcessing")
            .routeId("premiumProcessor")
            .log("Processing premium order in VIP lane")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("PREMIUM_PROCESSED: " + body);
                exchange.getIn().setHeader("processingTime", "IMMEDIATE");
                exchange.getIn().setHeader("assignedAgent", "VIP_AGENT_001");
            })
            .choice()
                // Further routing based on order amount
                .when(simple("${body} contains '\"amount\":2'"))  // Amount >= 2000
                    .log("High-value premium order - notifying management")
                    .to("direct:managementNotification")
                .otherwise()
                    .to("direct:premiumFulfillment");
        
        // Express order processing route
        from("direct:expressOrderProcessing")
            .routeId("expressProcessor")
            .log("Processing express order with priority handling")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("EXPRESS_PROCESSED: " + body);
                exchange.getIn().setHeader("processingTime", "PRIORITY");
                exchange.getIn().setHeader("shippingMethod", "OVERNIGHT");
            })
            .to("direct:expressFulfillment");
        
        // Standard order processing route
        from("direct:standardOrderProcessing")
            .routeId("standardProcessor")
            .log("Processing standard order through normal workflow")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("STANDARD_PROCESSED: " + body);
                exchange.getIn().setHeader("processingTime", "NORMAL");
                exchange.getIn().setHeader("shippingMethod", "STANDARD");
            })
            .to("direct:standardFulfillment");
        
        // Fulfillment routes
        from("direct:premiumFulfillment")
            .routeId("premiumFulfillment")
            .log("Premium order sent to VIP fulfillment center")
            .to("mock:premiumFulfillment");
        
        from("direct:expressFulfillment")
            .routeId("expressFulfillment")
            .log("Express order sent to expedited fulfillment")
            .to("mock:expressFulfillment");
        
        from("direct:standardFulfillment")
            .routeId("standardFulfillment")
            .log("Standard order sent to normal fulfillment")
            .to("mock:standardFulfillment");
        
        // Management notification for high-value orders
        from("direct:managementNotification")
            .routeId("managementNotification")
            .log("HIGH VALUE ALERT: Premium order requires management attention")
            .process(exchange -> {
                String originalBody = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("MANAGEMENT_ALERT: " + originalBody);
                exchange.getIn().setHeader("alertLevel", "HIGH");
                exchange.getIn().setHeader("notificationSent", System.currentTimeMillis());
            })
            .multicast()
                .to("mock:managementEmail")
                .to("direct:premiumFulfillment");
        
        // Manual review route
        from("direct:manualReview")
            .routeId("manualReview")
            .log("Order sent for manual review due to unknown type")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody("MANUAL_REVIEW_REQUIRED: " + body);
                exchange.getIn().setHeader("reviewReason", "UNKNOWN_ORDER_TYPE");
            })
            .to("mock:manualReviewQueue");
    }
}