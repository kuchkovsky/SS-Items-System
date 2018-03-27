package com.softserve.edu.util;

import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dao.UserItemDao;
import com.softserve.edu.dao.impl.UserDaoImpl;
import com.softserve.edu.dao.impl.UserItemDaoImpl;
import com.softserve.edu.service.LoginService;
import com.softserve.edu.service.UserItemsService;
import com.softserve.edu.service.UserService;

public class ApplicationContext {

    private static volatile ApplicationContext instance;

    private SqlPropertyReader sqlPropertyReader;
    private UserDao userDao;
    private UserItemDao userItemDao;
    private UserService userService;
    private UserItemsService userItemsService;
    private LoginService loginService;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            synchronized (ApplicationContext.class) {
                if (instance == null) {
                    instance = new ApplicationContext();
                    instance.initComponents();
                }
            }
        }
        return instance;
    }

    private void initComponents() {
        sqlPropertyReader = new SqlPropertyReader();
        userDao = new UserDaoImpl();
        userItemDao = new UserItemDaoImpl();
        userService = new UserService(userDao);
        userItemsService = new UserItemsService(userItemDao);
        loginService = new LoginService(userDao);
    }

    public SqlPropertyReader getSqlPropertyReader() {
        return sqlPropertyReader;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserItemDao getUserItemDao() {
        return userItemDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserItemsService getUserItemsService() {
        return userItemsService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

}
