package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeValueConstants;
import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.JspPathConstants;
import com.softserve.edu.constants.PagePathConstants;
import com.softserve.edu.exception.ServiceException;
import com.softserve.edu.service.UserItemsService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePathConstants.EDIT_USER_ITEM)
public class EditUserItemServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(AttributeConstants.PAGE_TITLE, AttributeValueConstants.EDIT_ITEM_PAGE);
        try {
            request.setAttribute(AttributeConstants.USER_ITEM, userItemsService.getUserItemFromUrlParameter(request));
            request.getRequestDispatcher(JspPathConstants.EDIT_USER_ITEM).forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userItemsService.editUserItem(request);
            response.sendRedirect(PagePathConstants.USER_ITEMS);
        } catch (ServiceException e) {
            request.setAttribute(AttributeConstants.ERROR, e);
            request.getRequestDispatcher(JspPathConstants.ERROR).forward(request, response);
        }
    }

}
