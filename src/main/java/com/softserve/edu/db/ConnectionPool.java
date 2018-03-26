package com.softserve.edu.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static volatile ConnectionPool instance;

    private HikariDataSource hikariDataSource;

    private ConnectionPool() {}

    public static ConnectionPool getInstance() {
        return getInstance(null);
    }

    public static ConnectionPool getInstance(HikariDataSource dataSource) {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        instance.checkStatus(dataSource);
        return instance;
    }

    private void checkStatus(HikariDataSource dataSource) {
        /*-		hikariDataSource		this.hikariDataSource		    Action
         * 			null			null				create default
         * 			null			not null			nothing
         * 			not null		null				save hikariDataSource
         * 			not null		not null			if equals then nothing
         */
        if (dataSource == null) {
            if (hikariDataSource == null) {
                setDataSource(DataSourceFactory.getDefault());
            }
        } else if (hikariDataSource == null || (!hikariDataSource.equals(dataSource))) {
            setDataSource(dataSource);
        }
    }

    private void setDataSource(HikariDataSource dataSource) {
        synchronized (ConnectionPool.class) {
            closeAllConnections();
            hikariDataSource = dataSource;
        }
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public HikariDataSource getDataSource() {
        return hikariDataSource;
    }

    public static void closeAllConnections() {
        if (instance != null) {
            if (instance.hikariDataSource != null) {
                instance.hikariDataSource.close();
            }
        }
    }

}
