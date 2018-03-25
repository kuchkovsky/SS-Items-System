package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AbstractCrudDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractCrudDao<UserEntity, Long> implements UserDao {

    public UserDaoImpl() {
        super("user");
    }

    @Override
    protected UserEntity createEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        return new UserEntity(id, login, password, firstName, lastName);
    }

    @Override
    protected List<Object> getEntityParams(UserEntity userEntity) {
        List<Object> params = new ArrayList<>();
        params.add(userEntity.getId());
        params.add(userEntity.getLogin());
        params.add((userEntity.getPassword()));
        params.add(userEntity.getFirstName());
        params.add(userEntity.getLastName());
        return params;
    }

    @Override
    public UserEntity findByLogin(String login) {
        return findOneByField(login, "login");
    }

}
