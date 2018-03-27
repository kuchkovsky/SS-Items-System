package com.softserve.edu.controller;

import com.softserve.edu.constants.AttributeValues;
import com.softserve.edu.constants.Attributes;
import com.softserve.edu.constants.JspPaths;
import com.softserve.edu.constants.PagePaths;
import com.softserve.edu.exception.ServiceException;
import com.softserve.edu.service.UserItemsService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePaths.EDIT_USER_ITEM)
public class EditUserItemServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Attributes.PAGE_TITLE, AttributeValues.EDIT_ITEM_PAGE);
        try {
            request.setAttribute(Attributes.USER_ITEM, userItemsService.getUserItemFromUrlParameter(request));
            request.getRequestDispatcher(JspPaths.EDIT_USER_ITEM).forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.ERROR, e);
            request.getRequestDispatcher(JspPaths.ERROR).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userItemsService.editUserItem(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.ERROR, e);
            request.getRequestDispatcher(JspPaths.ERROR).forward(request, response);
        }
    }

}
