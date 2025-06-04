package https.ega;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// User Processor - Processes the JSON response
class UserProcessor implements Processor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void process(Exchange exchange) throws Exception {
        JsonNode jsonResponse = exchange.getIn().getBody(JsonNode.class);
        
        if (jsonResponse != null && jsonResponse.isArray()) {
            int userCount = jsonResponse.size();
            exchange.getIn().setHeader("userCount", userCount);
            exchange.getIn().setHeader("users", jsonResponse);
            
            System.out.println("✅ Successfully fetched " + userCount + " users from API");
        } else {
            exchange.getIn().setHeader("userCount", 0);
            System.out.println("❌ Invalid or empty response from API");
        }
    }
}