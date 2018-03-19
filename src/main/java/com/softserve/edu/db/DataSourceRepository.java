package com.softserve.edu.db;

public final class DataSourceRepository {

    public static final String MARIADB_URL = "jdbc:mysql://localhost:3306/product_system";
    public static final String MARIADB_USER = "product_admin";
    public static final String MARIADB_PASSWORD = "pass12345";

    private DataSourceRepository() {
    }

    public static DataSource getDefault() {
        return getMariaDbLocalHost();
    }

    public static DataSource getMariaDbLocalHost() {
        return new DataSource(new org.mariadb.jdbc.Driver(), MARIADB_URL, MARIADB_USER, MARIADB_PASSWORD);
    }

}
