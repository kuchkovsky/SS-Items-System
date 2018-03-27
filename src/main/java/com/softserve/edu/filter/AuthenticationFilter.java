package com.softserve.edu.filter;

import com.softserve.edu.constants.Attributes;
import com.softserve.edu.constants.PagePaths;
import com.softserve.edu.service.UserService;
import com.softserve.edu.util.ApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(PagePaths.USER_PAGES)
public class AuthenticationFilter extends HttpFilter {

    private static final UserService userService = ApplicationContext.getInstance().getUserService();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(PagePaths.LOGIN);
        } else if (session.getAttribute(Attributes.USER_ID) == null) {
            session.invalidate();
            response.sendRedirect(PagePaths.LOGIN);
        } else {
            request.setAttribute(Attributes.FULL_USER_NAME, userService.getFullUserName(request));
            chain.doFilter(request, response);
        }
    }

}
