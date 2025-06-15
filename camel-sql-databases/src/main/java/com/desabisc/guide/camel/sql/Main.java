package com.desabisc.guide.camel.sql;

import com.desabisc.guide.camel.sql.configuration.DataSourceConfiguration;
import com.desabisc.guide.camel.sql.route.JdbcRouteEg;
import com.desabisc.guide.camel.sql.route.JpaRouteEg;
import com.desabisc.guide.camel.sql.route.SqlRouteEg;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {

            // jdbc example
            //camelContext.getRegistry().bind("pgDataSource", DataSourceConfiguration.getDataSource());
            //camelContext.addRoutes(new JdbcRouteEg());

            // jpa example
            //camelContext.addRoutes(new JpaRouteEg());

            // sql example
            // it will discover datasource automatically
            camelContext.getRegistry().bind("pgDataSource", DataSourceConfiguration.getDataSource());
            camelContext.addRoutes(new SqlRouteEg());

            camelContext.start();
            Thread.sleep(TimeUnit.SECONDS.toMillis(300));
        }
    }
}