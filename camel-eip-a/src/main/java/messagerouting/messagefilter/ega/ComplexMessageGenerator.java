package messagerouting.messagefilter.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// ==========================================
// Message Generator Classes
// ==========================================
// These processors generate different types of test messages
/**
 * Generates complex messages for multiple filter conditions
 */
class ComplexMessageGenerator implements Processor {
    private int counter = 0;
    private final String[] urgencyLevels = {"NORMAL", "URGENT", "ROUTINE"};
    private final String[] departments = {"SALES", "MARKETING", "SUPPORT", "FINANCE"};
    private final Boolean[] vipStatus = {false, true, false, false, true};
    private final Double[] amounts = {1000.0, 7500.0, 2000.0, 500.0, 10000.0};

    @Override
    public void process(Exchange exchange) {
        String urgency = urgencyLevels[counter % urgencyLevels.length];
        exchange.getIn().setBody(urgency + " message #" + (counter + 1));
        exchange.getIn().setHeader("Department", departments[counter % departments.length]);
        exchange.getIn().setHeader("VIP", vipStatus[counter % vipStatus.length]);
        exchange.getIn().setHeader("Amount", amounts[counter % amounts.length]);
        counter++;
    }
}