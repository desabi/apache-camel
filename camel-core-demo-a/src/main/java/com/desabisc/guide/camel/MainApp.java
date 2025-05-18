package com.desabisc.guide.camel;

//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.main.Main; // This should work with camel-main 4.11.0

public class MainApp {

  /*
  public static void main(String[] args) {
    // Create a Camel Main instance
    Main main = new Main();

    // Add routes
    main.configure().addRoutesBuilder(new MyRouteBuilder());

    // Set properties if needed
    // main.configure().setPropertyPlaceholderLocations("classpath:application.properties");

    System.out.println("Starting Camel application...");

    // Start the Camel application
    main.run();
  }

  // Inner RouteBuilder class (you can also create a separate class file)
  public static class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
      // Define your routes here
      from("timer:simple?period=5000")
          .setBody(constant("Hello Camel!"))
          .log(">>> ${body}");
    }
  }*/
}