package messagerouting.messagefilter.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// ==========================================
// Message Generator Classes
// ==========================================
// These processors generate different types of test messages

/**
 * Generates messages with varying content for content-based filtering
 */
class MessageGenerator implements Processor {
    private int counter = 0;
    private final String[] messages = {
            "Regular message #1",
            "IMPORTANT: System maintenance required",
            "Daily report #2",
            "IMPORTANT: Security alert detected",
            "Regular notification #3"
    };

    @Override
    public void process(Exchange exchange) {
        String message = messages[counter % messages.length];
        exchange.getIn().setBody(message);
        counter++;
    }
}