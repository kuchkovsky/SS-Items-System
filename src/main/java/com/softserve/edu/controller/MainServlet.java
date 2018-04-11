package com.softserve.edu.controller;

import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.service.LoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.ROOT_SERVLET)
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (LoginService.getLoggedUserId(request) != null) {
            response.sendRedirect(PagePathConstants.USER_ITEMS);
        } else {
            response.sendRedirect(PagePathConstants.LOGIN);
        }
    }

}
