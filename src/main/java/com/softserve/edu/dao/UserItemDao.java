package com.softserve.edu.dao;

import com.softserve.edu.entity.UserItemEntity;

import java.util.List;

public interface UserItemDao extends CrudDao<UserItemEntity, Long> {

    List<UserItemEntity> findByUserId(Long id);

}
