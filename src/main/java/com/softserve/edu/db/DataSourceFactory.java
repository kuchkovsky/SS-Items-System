package com.softserve.edu.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceFactory {

    public static HikariDataSource getDefault() {
        return getMariaDb();
    }

    public static HikariDataSource getMariaDb() {
        return getDataSourceFromFile("/datasource/mariadb.properties");
    }

    public static HikariDataSource getPostgreSql() {
        return getDataSourceFromFile("/datasource/postgresql.properties");
    }

    private static HikariDataSource getDataSourceFromFile(String fileName) {
        return new HikariDataSource(new HikariConfig(fileName));
    }

}
