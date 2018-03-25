package com.softserve.edu.controller;

import com.softserve.edu.constant.AttributeValues;
import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
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

@WebServlet(PagePaths.SIGNUP)
public class SignupServlet extends HttpServlet {

    private static final UserService userService = ApplicationContext.getInstance().getUserService();
    private static final LoginService loginService = ApplicationContext.getInstance().getLoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Attributes.PAGE_TITLE, AttributeValues.SIGN_UP_PAGE);
        getServletContext().getRequestDispatcher(JspPaths.USER_ACCOUNT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userService.createUser(request);
            loginService.loginUser(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (EmptyFieldsException e) {
            request.setAttribute(Attributes.ERROR, e);
            getServletContext().getRequestDispatcher(JspPaths.ERROR).forward(request, response);
        } catch (UserAlreadyExistsException | PasswordsDontMatchException e) {
            request.setAttribute(Attributes.ERROR, e);
            doGet(request, response);
        } catch (FailedLoginException e) {
            response.sendRedirect(PagePaths.LOGIN);
        }
    }

}
