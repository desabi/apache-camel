package com.desabisc.guide.camel;

import com.desabisc.guide.camel.route.ExampleRoute;
import com.desabisc.guide.camel.storage.ExamplePetStorage;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {

           camelContext.addRoutes(new ExampleRoute());
           camelContext.getRegistry().bind("examplePetStorage", new ExamplePetStorage());

           camelContext.start();
           Thread.sleep(TimeUnit.SECONDS.toMillis(300)); // // 3000000
        }
    }
}