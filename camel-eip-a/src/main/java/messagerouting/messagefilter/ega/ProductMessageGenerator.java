package messagerouting.messagefilter.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// ==========================================
// Message Generator Classes
// ==========================================
// These processors generate different types of test messages
/**
 * Generates product messages for method-based filtering
 */
class ProductMessageGenerator implements Processor {
        private int counter = 0;
        private final String[] categories = {"ELECTRONICS", "CLOTHING", "BOOKS", "HOME"};
        private final Boolean[] stockStatus = {true, false, true, true, false};
        
        @Override
        public void process(Exchange exchange) {
            exchange.getIn().setBody("PRODUCT-" + String.format("%03d", counter + 1));
            exchange.getIn().setHeader("Category", categories[counter % categories.length]);
            exchange.getIn().setHeader("InStock", stockStatus[counter % stockStatus.length]);
            counter++;
        }
    }