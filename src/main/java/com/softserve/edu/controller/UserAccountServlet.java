package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeValueConstants;
import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.JspPathConstants;
import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.exception.EmptyFieldsException;
import com.softserve.edu.exception.PasswordsDontMatchException;
import com.softserve.edu.service.UserService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.USER_ACCOUNT)
public class UserAccountServlet extends HttpServlet {

    private static final UserService userService = ApplicationContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute(AttributeConstants.USER) == null) {
            request.setAttribute(AttributeConstants.USER, userService.getUser(request));
        }
        request.setAttribute(AttributeConstants.PAGE_TITLE, AttributeValueConstants.ACCOUNT_PAGE);
        request.getRequestDispatcher(JspPathConstants.USER_ACCOUNT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userService.editUser(request);
            response.sendRedirect(PagePathConstants.USER_ITEMS);
        } catch (EmptyFieldsException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        } catch (PasswordsDontMatchException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            doGet(request, response);
        }
    }

}
