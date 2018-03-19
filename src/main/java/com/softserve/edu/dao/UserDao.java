package com.softserve.edu.dao;

import com.softserve.edu.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity, Long> {

    UserEntity findByLogin(String login);

}
