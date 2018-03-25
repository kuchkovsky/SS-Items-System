package com.softserve.edu.service;

import com.softserve.edu.constant.Attributes;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dto.UserDto;
import com.softserve.edu.entity.UserEntity;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.exception.PasswordsDontMatchException;
import com.softserve.edu.exception.UserAlreadyExistsException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(HttpServletRequest request)
            throws IncorrectParametersException, UserAlreadyExistsException, PasswordsDontMatchException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password-confirm");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordConfirm)
                || StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            throw new IncorrectParametersException();
        }
        if (userDao.findByLogin(login) != null) {
            request.setAttribute(Attributes.USER, new UserDto(null, null, firstName, lastName));
            throw new UserAlreadyExistsException("User with the same login already exists. Please use another login");
        }
        if (!password.equals(passwordConfirm)) {
            request.setAttribute(Attributes.USER, new UserDto(login, null, firstName, lastName));
            throw new PasswordsDontMatchException("Passwords don't match. Please try again");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDao.save(new UserEntity(login, hashedPassword, firstName, lastName));
    }

    public void editUser(HttpServletRequest request) throws IncorrectParametersException, PasswordsDontMatchException {
        Long id = LoginService.getLoggedUserId(request);
        String login = userDao.findById(id).getLogin();
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password-confirm");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordConfirm)
                || StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            throw new IncorrectParametersException();
        }
        if (!password.equals(passwordConfirm)) {
            request.setAttribute(Attributes.USER, new UserDto(login, null, firstName, lastName));
            throw new PasswordsDontMatchException("Passwords don't match. Please try again");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDao.save(new UserEntity(id, login, hashedPassword, firstName, lastName));
    }

    public UserDto getUser(HttpServletRequest request) {
        return new UserDto(userDao.findById(LoginService.getLoggedUserId(request)));
    }

    public String getFullUserName(HttpServletRequest request) {
       return new UserDto(userDao.findById(LoginService.getLoggedUserId(request))).getFullUserName();
    }

}
