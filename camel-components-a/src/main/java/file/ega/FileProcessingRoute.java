package file.ega;// Route Builder Class

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Route 1: Basic file polling and processing
        from("file:input?noop=true&delay=2000")
                .routeId("file-processor-route")
                .log("Processing file: ${header.CamelFileName}")
                .process(new FileContentProcessor())
                .to("file:output");

        // Route 2: File polling with filtering and error handling
        from("file:input/csv?include=.*\\.csv&move=processed&moveFailed=error")
                .routeId("csv-processor-route")
                .log("Processing CSV file: ${header.CamelFileName}")
                // .onException() is Configuration, Not Execution
                // It configures how exceptions should be handled
                // It doesn't execute at that point in the route
                .onException(Exception.class)                  // ← Sets up exception handling
                    .handled(true)
                    .log("Error processing file: ${exception.message}")
                    .to("file:error")
                .end()
                // Scope Coverage
                // The exception handler applies to all subsequent steps in the route
                // Everything after .end() is protected by this exception handler
                // Both .process(new CsvProcessor()) and .to("file:output/csv") are covered
                .process(new CsvProcessor())
                .to("file:output/csv");

        // Route 3: File creation example
        from("timer:fileCreator?period=10000")
                .routeId("file-creator-route")
                .setBody(constant("Hello from Camel at ${date:now:yyyy-MM-dd HH:mm:ss}"))
                .setHeader(Exchange.FILE_NAME, simple("generated-${date:now:yyyyMMdd-HHmmss}.txt"))
                .to("file:generated")    // ← File component creates the file here
                .log("Created file: ${header.CamelFileName}");
    }
}