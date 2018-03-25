package com.softserve.edu.db;

import com.softserve.edu.util.ApplicationContext;
import com.softserve.edu.util.JdbcPropertyReader;

public final class DataSourceRepository {

    private static final JdbcPropertyReader jdbcProperty = ApplicationContext.getInstance().getJdbcPropertyReader();

    private DataSourceRepository() {}

    public static DataSource getDefault() {
        return getMariaDbLocalHost();
    }

    public static DataSource getMariaDbLocalHost() {
        String url = jdbcProperty.get("mariadb.url");
        String user = jdbcProperty.get("mariadb.user");
        String password = jdbcProperty.get("mariadb.password");
        return new DataSource(new org.mariadb.jdbc.Driver(), url, user, password);
    }

}
