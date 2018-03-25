package com.softserve.edu.controller;

import com.softserve.edu.constant.AttributeValues;
import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
import com.softserve.edu.exception.AuthorizationException;
import com.softserve.edu.exception.IncorrectParametersException;
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
            request.setAttribute(Attributes.USER_ITEM, userItemsService.getUserItemFromParameter(request));
            getServletContext().getRequestDispatcher(JspPaths.EDIT_USER_ITEM).forward(request, response);
        } catch (IncorrectParametersException e) {
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (AuthorizationException e) {
            response.sendRedirect(PagePaths.LOGIN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userItemsService.editUserItem(request);
            response.sendRedirect(PagePaths.USER_ITEMS);
        } catch (IncorrectParametersException e) {
            response.sendRedirect(PagePaths.ADD_USER_ITEM);
        } catch (AuthorizationException e) {
            response.sendRedirect(PagePaths.LOGIN);
        }
    }

}