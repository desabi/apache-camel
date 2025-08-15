package seda.ega;

import file.ega.FileProcessorApplication;
import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelSedaApp {

  private static final Logger logger = LoggerFactory.getLogger(CamelSedaApp.class);

  public static void main(String[] args) {
    CamelContext camelContext = new DefaultCamelContext();

    try {
      // Add the route to the context
      camelContext.addRoutes(new SedaRouteBuilder());

      // Start the context
      camelContext.start();
      logger.info("Camel Context started successfully");

      // Keep the application running
      Thread.sleep(TimeUnit.SECONDS.toMillis(30)); // Run for 30 seconds
    } catch (Exception e) {
      logger.error("Error running Camel Seda Application", e);
    } finally {
      camelContext.stop();
      logger.info("Camel Context stopped. Application terminated.");
    }
  }

}
