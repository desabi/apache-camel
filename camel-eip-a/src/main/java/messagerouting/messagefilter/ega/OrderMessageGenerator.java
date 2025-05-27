package messagerouting.messagefilter.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// ==========================================
// Message Generator Classes
// ==========================================
// These processors generate different types of test messages
/**
 * Generates order messages for custom predicate filtering
 */
class OrderMessageGenerator implements Processor {
        private int counter = 0;
        private final String[] regions = {"NORTH_AMERICA", "EUROPE", "ASIA", "SOUTH_AMERICA"};
        private final Double[] amounts = {500.0, 1500.0, 750.0, 2500.0, 300.0};
        
        @Override
        public void process(Exchange exchange) {
            exchange.getIn().setBody("ORDER-" + String.format("%04d", counter + 1));
            exchange.getIn().setHeader("Region", regions[counter % regions.length]);
            exchange.getIn().setHeader("Amount", amounts[counter % amounts.length]);
            counter++;
        }
    }