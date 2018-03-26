package com.softserve.edu.controller;

import com.softserve.edu.constant.PagePaths;
import com.softserve.edu.service.LoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePaths.ROOT_SERVLET)
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (LoginService.getLoggedUserId(request) != null) {
            response.sendRedirect(PagePaths.USER_ITEMS);
        } else {
            response.sendRedirect(PagePaths.LOGIN);
        }
    }

}
