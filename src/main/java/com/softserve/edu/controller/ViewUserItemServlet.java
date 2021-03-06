package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.JspPathConstants;
import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.exception.AccessViolationException;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.exception.ResourceNotFoundException;
import com.softserve.edu.service.UserItemsService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.VIEW_USER_ITEM)
public class ViewUserItemServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute(AttributeConstants.USER_ITEM, userItemsService.getUserItemFromUrl(request));
            request.getRequestDispatcher(JspPathConstants.VIEW_USER_ITEM).forward(request, response);
        } catch (IncorrectParametersException | ResourceNotFoundException | AccessViolationException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        }
    }

}
