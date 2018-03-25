package com.softserve.edu.controller;

import com.softserve.edu.constant.PagePaths;
import com.softserve.edu.db.ConnectionManager;
import com.softserve.edu.service.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class MainServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (LoginService.getLoggedUserId(request) != null) {
            response.sendRedirect(PagePaths.USER_ITEMS);
        } else {
            response.sendRedirect(PagePaths.LOGIN);
        }
    }

    @Override
    public void destroy() {
        logger.info("Closing all JDBC connections...");
        ConnectionManager.closeAllConnections();
    }

}
