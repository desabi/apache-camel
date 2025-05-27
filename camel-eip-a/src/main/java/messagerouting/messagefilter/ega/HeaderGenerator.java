package messagerouting.messagefilter.ega;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// ==========================================
// Message Generator Classes
// ==========================================
// These processors generate different types of test messages
/**
 * Generates messages with different header values for header-based filtering
 */
class HeaderGenerator implements Processor {
    private int counter = 0;
    private final String[] priorities = {"LOW", "MEDIUM", "HIGH", "CRITICAL"};
    private final String[] types = {"INFO", "WARNING", "ERROR", "DEBUG"};

    @Override
    public void process(Exchange exchange) {
        exchange.getIn().setBody("Message #" + (counter + 1));
        exchange.getIn().setHeader("Priority", priorities[counter % priorities.length]);
        exchange.getIn().setHeader("MessageType", types[counter % types.length]);
        counter++;
    }
}