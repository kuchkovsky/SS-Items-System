package com.softserve.edu.controller;

import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.exception.FailedLoginException;
import com.softserve.edu.service.LoginService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePaths.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final LoginService loginService = ApplicationContext.getInstance().getLoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPaths.LOGIN).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            loginService.loginUser(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (IncorrectParametersException e) {
            response.sendRedirect(PagePaths.LOGIN);
        } catch (FailedLoginException e) {
            request.setAttribute(Attributes.ERROR, e);
            request.getRequestDispatcher(JspPaths.LOGIN).forward(request, response);
        }
    }

}
