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

@WebServlet(PagePathConstants.USER_ITEMS)
public class UserItemsServlet extends HttpServlet {

    private static final UserItemsService userItemsService = ApplicationContext.getInstance().getUserItemsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(AttributeConstants.USER_ITEMS, userItemsService.getUserItems(request));
        request.getRequestDispatcher(JspPathConstants.USER_ITEMS).forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            userItemsService.deleteUserItem(request);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IncorrectParametersException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (AccessViolationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
