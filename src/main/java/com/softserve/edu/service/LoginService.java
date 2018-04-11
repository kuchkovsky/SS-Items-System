package com.softserve.edu.service;

import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.FormParameterConstants;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.UserEntity;
import com.softserve.edu.exception.EmptyFieldsException;
import com.softserve.edu.exception.FailedLoginException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginService {

    public static final int SESSION_TIMEOUT_IN_DAYS = 3;

    public static final String EMPTY_LOGIN_FIELDS_MESSAGE = "Incorrect login parameters. Fields can't be empty";
    public static final String LOGIN_FAILED_MESSAGE = "Incorrect login or password. Please try again";

    private UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void loginUser(HttpServletRequest request) throws EmptyFieldsException, FailedLoginException {
        String login = request.getParameter(FormParameterConstants.LOGIN);
        String password = request.getParameter(FormParameterConstants.PASSWORD);
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            throw new EmptyFieldsException(EMPTY_LOGIN_FIELDS_MESSAGE);
        }
        UserEntity user = userDao.findByLogin(login);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new FailedLoginException(LOGIN_FAILED_MESSAGE);
        }
        logout(request);
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(SESSION_TIMEOUT_IN_DAYS * 24 * 60 * 60);
        session.setAttribute(AttributeConstants.USER_ID, user.getId());
    }

    public static Long getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Long) session.getAttribute(AttributeConstants.USER_ID);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
