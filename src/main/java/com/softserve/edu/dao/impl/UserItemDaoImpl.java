package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AbstractCrudDao;
import com.softserve.edu.dao.UserItemDao;
import com.softserve.edu.entity.UserItemEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserItemDaoImpl extends AbstractCrudDao<UserItemEntity, Long> implements UserItemDao {

    public UserItemDaoImpl() {
        super("userItem");
    }

    @Override
    protected UserItemEntity createEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Long userId = resultSet.getLong("user_id");
        return new UserItemEntity(id, title, description, userId);
    }

    @Override
    protected List<Object> getEntityParams(UserItemEntity userItemEntity) {
        List<Object> params = new ArrayList<>();
        params.add(userItemEntity.getId());
        params.add(userItemEntity.getTitle());
        params.add(userItemEntity.getDescription());
        params.add(userItemEntity.getUserId());
        return params;
    }

    @Override
    public List<UserItemEntity> findByUserId(Long id) {
        return findAllByField(id, "user_id");
    }

}
