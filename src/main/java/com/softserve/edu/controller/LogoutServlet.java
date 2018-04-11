package com.softserve.edu.controller;

import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.service.LoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.LOGOUT)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService.logout(request);
        response.sendRedirect(PagePathConstants.LOGIN);
    }

}
