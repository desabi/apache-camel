package com.desabisc.guide.camel.sql.configuration;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class DataSourceConfiguration {
    private static final PGSimpleDataSource dataSource;

    static {
        dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5432});
        dataSource.setDatabaseName("db_tests");
        dataSource.setCurrentSchema("public");
        dataSource.setUser("postgres");
        dataSource.setPassword("desaps");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}