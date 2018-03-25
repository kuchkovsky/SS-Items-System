package com.softserve.edu.controller;

import com.softserve.edu.constant.Attributes;
import com.softserve.edu.constant.JspPaths;
import com.softserve.edu.constant.PagePaths;
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

@WebServlet(PagePaths.VIEW_USER_ITEM)
public class ViewUserItemServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute(Attributes.USER_ITEM, userItemsService.getUserItemFromUrl(request));
            getServletContext().getRequestDispatcher(JspPaths.VIEW_USER_ITEM).forward(request, response);
        } catch (IncorrectParametersException | ResourceNotFoundException | AccessViolationException e) {
            request.setAttribute(Attributes.ERROR, e);
            getServletContext().getRequestDispatcher(JspPaths.ERROR).forward(request, response);
        }
    }

}
