package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.JspPathConstants;
import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.exception.EmptyFieldsException;
import com.softserve.edu.exception.FailedLoginException;
import com.softserve.edu.service.LoginService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final LoginService loginService = ApplicationContext.getInstance().getLoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(JspPathConstants.LOGIN).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            loginService.loginUser(request);
            response.sendRedirect(PagePathConstants.USER_ITEMS);
        } catch (EmptyFieldsException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        } catch (FailedLoginException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.LOGIN).forward(request, response);
        }
    }

}
