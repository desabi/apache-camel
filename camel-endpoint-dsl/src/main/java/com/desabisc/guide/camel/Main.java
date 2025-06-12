package com.desabisc.guide.camel;

import com.desabisc.guide.camel.component.LocalSftp;
import com.desabisc.guide.camel.route.ExampleRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new ExampleRoute());
            camelContext.addComponent("localSftp", new LocalSftp().localSftp());
            camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
            camelContext.start();
            Thread.sleep(TimeUnit.SECONDS.toMillis(3000)); // 3000000
        }
    }
}