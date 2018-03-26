package com.softserve.edu.util;

import com.softserve.edu.db.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ApplicationContextListener .class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting application...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Closing all JDBC connections...");
        ConnectionPool.closeAllConnections();
        logger.info("Exiting application...");
    }

}
