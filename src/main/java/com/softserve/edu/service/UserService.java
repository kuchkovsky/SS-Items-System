package com.softserve.edu.service;

import com.softserve.edu.constants.AttributeConstants;
import com.softserve.edu.constants.FormParameterConstants;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dto.UserDto;
import com.softserve.edu.entity.UserEntity;
import com.softserve.edu.exception.EmptyFieldsException;
import com.softserve.edu.exception.PasswordsDontMatchException;
import com.softserve.edu.exception.UserAlreadyExistsException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;

public class UserService {

    public static final String EMPTY_USER_FIELDS_MESSAGE = "Incorrect user parameters. Fields can't be empty";
    public static final String USER_ALREADY_EXISTS_MESSAGE
            = "User with the same login already exists. Please use another login";
    public static final String PASSWORDS_DONT_MATCH_MESSAGE = "Passwords don't match. Please try again";

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(HttpServletRequest request)
            throws EmptyFieldsException, UserAlreadyExistsException, PasswordsDontMatchException {
        String passwordConfirm = request.getParameter(FormParameterConstants.PASSWORD_CONFIRM);
        createUser(getUserEntityFromRequest(request, false), passwordConfirm, request);
    }

    public void editUser(HttpServletRequest request) throws EmptyFieldsException, PasswordsDontMatchException {
        String passwordConfirm = request.getParameter(FormParameterConstants.PASSWORD_CONFIRM);
        editUser(getUserEntityFromRequest(request, true), passwordConfirm, request);
    }

    private UserEntity getUserEntityFromRequest(HttpServletRequest request, boolean useLoggedUserId) {
        Long id = null;
        String login;
        if (useLoggedUserId) {
            id = LoginService.getLoggedUserId(request);
            login = userDao.findById(id).getLogin();
        } else {
            login = request.getParameter(FormParameterConstants.LOGIN);
        }
        String password = request.getParameter(FormParameterConstants.PASSWORD);
        String firstName = request.getParameter(FormParameterConstants.FIRST_NAME);
        String lastName = request.getParameter(FormParameterConstants.LAST_NAME);
        return new UserEntity(id, login, password, firstName, lastName);
    }

    private void createUser(UserEntity user, String passwordConfirm, HttpServletRequest request)
            throws EmptyFieldsException, UserAlreadyExistsException, PasswordsDontMatchException {
        checkFields(user, passwordConfirm);
        checkIfUserExists(user, request);
        checkPasswordConfirm(user, passwordConfirm, request);
        saveUser(user);
    }

    private void editUser(UserEntity user, String passwordConfirm, HttpServletRequest request)
            throws EmptyFieldsException, PasswordsDontMatchException {
        checkFields(user, passwordConfirm);
        checkPasswordConfirm(user, passwordConfirm, request);
        saveUser(user);
    }

    private void checkFields(UserEntity user, String passwordConfirm) throws EmptyFieldsException {
        if (user.hasEmptyFields() || StringUtils.isEmpty(passwordConfirm)) {
            throw new EmptyFieldsException(EMPTY_USER_FIELDS_MESSAGE);
        }
    }

    private void checkIfUserExists(UserEntity user, HttpServletRequest request) throws UserAlreadyExistsException {
        if (userDao.findByLogin(user.getLogin()) != null) {
            user.setLogin(null);
            user.setPassword(null);
            request.setAttribute(AttributeConstants.USER, new UserDto(user));
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE);
        }
    }

    private void checkPasswordConfirm(UserEntity user, String passwordConfirm, HttpServletRequest request)
            throws PasswordsDontMatchException {
        if (!user.getPassword().equals(passwordConfirm)) {
            user.setPassword(null);
            request.setAttribute(AttributeConstants.USER, new UserDto(user));
            throw new PasswordsDontMatchException(PASSWORDS_DONT_MATCH_MESSAGE);
        }
    }

    private void saveUser(UserEntity user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userDao.save(user);
    }

    public UserDto getUser(HttpServletRequest request) {
        return new UserDto(userDao.findById(LoginService.getLoggedUserId(request)));
    }

    public String getFullUserName(HttpServletRequest request) {
       return new UserDto(userDao.findById(LoginService.getLoggedUserId(request))).getFullUserName();
    }

}
