package com.softserve.edu.controller;

import com.softserve.edu.constant.AttributeValues;
import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
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

@WebServlet(PagePaths.USER_ACCOUNT)
public class UserAccountServlet extends HttpServlet {

    private static final UserService userService = ApplicationContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute(Attributes.USER) == null) {
            request.setAttribute(Attributes.USER, userService.getUser(request));
        }
        request.setAttribute(Attributes.PAGE_TITLE, AttributeValues.ACCOUNT_PAGE);
        request.getRequestDispatcher(JspPaths.USER_ACCOUNT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userService.editUser(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (EmptyFieldsException e) {
            request.setAttribute(Attributes.ERROR, e);
            request.getRequestDispatcher(JspPaths.ERROR).forward(request, response);
        } catch (PasswordsDontMatchException e) {
            request.setAttribute(Attributes.ERROR, e);
            doGet(request, response);
        }
    }

}
