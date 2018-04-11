package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeValueConstants;
import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.JspPathConstants;
import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.exception.*;
import com.softserve.edu.service.LoginService;
import com.softserve.edu.service.UserService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.SIGNUP)
public class SignupServlet extends HttpServlet {

    private static final UserService userService = ApplicationContext.getInstance().getUserService();
    private static final LoginService loginService = ApplicationContext.getInstance().getLoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(AttributeConstants.PAGE_TITLE, AttributeValueConstants.SIGN_UP_PAGE);
        request.getRequestDispatcher(JspPathConstants.USER_ACCOUNT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userService.createUser(request);
            loginService.loginUser(request);
            response.sendRedirect(PagePathConstants.USER_ITEMS);
        } catch (EmptyFieldsException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        } catch (UserAlreadyExistsException | PasswordsDontMatchException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            doGet(request, response);
        } catch (FailedLoginException e) {
            response.sendRedirect(PagePathConstants.LOGIN);
        }
    }

}
