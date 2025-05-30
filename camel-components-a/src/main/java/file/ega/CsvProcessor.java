package file.ega;

// CSV Specific Processor
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

class CsvProcessor implements Processor {
    
    @Override
    public void process(Exchange exchange) {
        String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        String csvContent = exchange.getIn().getBody(String.class);
        
        // Simple CSV processing (count lines and add summary)
        String[] lines = csvContent.split("\n");
        int recordCount = lines.length - 1; // Assuming first line is header
        
        String summary = String.format("""
            CSV Processing Summary
            =====================
            File: %s
            Total Records: %d
            Processing Date: %s
            
            Original Content:
            %s
            """, 
            fileName, 
            recordCount, 
            java.time.LocalDateTime.now(),
            csvContent
        );
        
        exchange.getIn().setBody(summary);
        exchange.getIn().setHeader(Exchange.FILE_NAME, "summary_" + fileName);
    }
}