package https.ega;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

// Route Builder Class
class UserApiRoute extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
        
        // Configure JSON data format (Apache Camel Jackson Required)
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat();
        jsonDataFormat.setUnmarshalType(JsonNode.class);
        
        // Main route to fetch users
        from("timer://userFetcher?period=5000&repeatCount=2")
            .routeId("fetch-users-route")
            .log("Starting to fetch users from GoRest API...")
            
            // Set HTTP headers
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader("Accept", constant("application/json"))
            .setHeader("User-Agent", constant("Apache-Camel-4.8-Example"))
            
            // Make HTTP call to GoRest API
            .to("https://gorest.co.in/public/v2/users")
            
            // Log response status
            .log("HTTP Response Status: ${header.CamelHttpResponseCode}")
            
            // Unmarshal JSON response
            .unmarshal(jsonDataFormat)
            
            // Process the response
            .process(new UserProcessor())
            
            // Route to different endpoints based on processing
            .choice()
                .when(header("userCount").isGreaterThan(0))
                    .to("direct:processUsers")
                .otherwise()
                    .log("No users found in the response")
            .end();
        
        // Route to process users
        from("direct:processUsers")
            .routeId("process-users-route")
            .log("Processing ${header.userCount} users...")
            .process(new UserDetailsProcessor())
            .to("direct:logUsers");
        
        // Route to log user details
        from("direct:logUsers")
            .routeId("log-users-route")
            .log("User processing completed. Check logs for details.");
    }
}