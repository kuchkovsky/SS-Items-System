package com.softserve.edu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class ConnectionManager {

    private final static String FAILED_REGISTRATE_DRIVER = "Failed to Registrate JDBC Driver";
    private final static String FAILED_CREATE_CONNECTION = "Failed to Create Connection";
    private final static String FAILED_CLOSE_CONNECTION = "Failed to Close Connection";

    private static volatile ConnectionManager instance;
    private DataSource dataSource;
    private final HashMap<Long, Connection> connections = new HashMap<>();

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        return getInstance(null);
    }

    public static ConnectionManager getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        instance.checkStatus(dataSource);
        return instance;
    }

    private void checkStatus(DataSource dataSource) {
        /*-		dataSource		this.dataSource		    Action
         * 			null			null				create default
         * 			null			not null			nothing
         * 			not null		null				save dataSource
         * 			not null		not null			if equals then nothing
         */
        if (dataSource == null) {
            if (this.dataSource == null) {
                setDataSource(DataSourceRepository.getDefault());
            }
        } else if (this.dataSource == null || (!getDataSource().equals(dataSource))) {
            setDataSource(dataSource);
        }
    }

    private void setDataSource(DataSource dataSource) {
        synchronized (ConnectionManager.class) {
            this.dataSource = dataSource;
            registerDriver();
            closeAllConnections();
        }
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver(getDataSource().getJdbcDriver());
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_REGISTRATE_DRIVER, e);
        }
    }

    private HashMap<Long, Connection> getAllConnections() {
        return this.connections;
    }

    private void addConnection(Connection connection) {
        getAllConnections().put(Thread.currentThread().getId(), connection);
    }

    public Connection getConnection() {
        Connection connection = getAllConnections().get(Thread.currentThread().getId());
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(getDataSource().getConnectionUrl(),
                        getDataSource().getUsername(), getDataSource().getPassword());
            } catch (SQLException e) {
                throw new RuntimeException(FAILED_CREATE_CONNECTION, e);
            }
            addConnection(connection);
        }
        return connection;
    }

    public static void closeAllConnections() {
        if (instance != null) {
            for (Long key : instance.getAllConnections().keySet()) {
                if (instance.getAllConnections().get(key) != null) {
                    try {
                        instance.getAllConnections().get(key).close();
                    } catch (SQLException e) {
                        throw new RuntimeException(FAILED_CLOSE_CONNECTION, e);
                    }
                    instance.getAllConnections().put(key, null);
                }
            }
        }
    }

}
