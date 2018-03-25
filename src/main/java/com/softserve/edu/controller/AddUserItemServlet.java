package com.softserve.edu.controller;

import com.softserve.edu.constant.AttributeValues;
import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.service.UserItemsService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PagePaths.ADD_USER_ITEM)
public class AddUserItemServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Attributes.PAGE_TITLE, AttributeValues.ADD_ITEM_PAGE);
        getServletContext().getRequestDispatcher(JspPaths.EDIT_USER_ITEM).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userItemsService.createUserItem(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (IncorrectParametersException e) {
            response.sendRedirect(PagePaths.ADD_USER_ITEM);
        }
    }

}
