package file.ega;

// Custom Processor for file content

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.nio.charset.StandardCharsets;

class FileContentProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        String content = exchange.getIn().getBody(String.class);

        // Process the content (example: convert to uppercase and add metadata)
        String processedContent = String.format("""
                        === File Processing Report ===
                        Original File: %s
                        Processing Time: %s
                        Content Length: %d characters
                        
                        === Processed Content ===
                        %s
                        
                        === End of Report ===
                        """,
                fileName,
                java.time.LocalDateTime.now(),
                content.length(),
                content.toUpperCase()
        );

        // Set the processed content back to the exchange
        exchange.getIn().setBody(processedContent);

        // Modify the filename for output
        String newFileName = "processed_" + fileName;
        exchange.getIn().setHeader(Exchange.FILE_NAME, newFileName);
    }
}