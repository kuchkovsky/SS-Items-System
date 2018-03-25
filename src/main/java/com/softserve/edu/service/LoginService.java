package com.softserve.edu.service;

import com.softserve.edu.constant.Attributes;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.UserEntity;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.exception.FailedLoginException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginService {

    public static final int SESSION_TIMEOUT_IN_DAYS = 3;

    private UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void loginUser(HttpServletRequest request) throws IncorrectParametersException, FailedLoginException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            throw new IncorrectParametersException();
        }
        UserEntity user = userDao.findByLogin(login);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new FailedLoginException("Incorrect login or password. Please try again");
        }
        logout(request);
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(SESSION_TIMEOUT_IN_DAYS * 24 * 60 * 60);
        session.setAttribute(Attributes.USER_ID, user.getId());
    }

    public static Long getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Long) session.getAttribute(Attributes.USER_ID);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
