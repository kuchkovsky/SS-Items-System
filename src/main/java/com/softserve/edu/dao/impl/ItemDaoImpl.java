package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AbstractCrudDao;
import com.softserve.edu.dao.ItemDao;
import com.softserve.edu.entity.ItemEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends AbstractCrudDao<ItemEntity, Long> implements ItemDao {

    public ItemDaoImpl() {
        super("item");
    }


    @Override
    protected ItemEntity createEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Long userId = resultSet.getLong("user_id");
        return new ItemEntity(id, title, description, userId);
    }

    @Override
    protected List<Object> getEntityParams(ItemEntity itemEntity) {
        List<Object> params = new ArrayList<>();
        params.add(itemEntity.getId());
        params.add(itemEntity.getTitle());
        params.add(itemEntity.getDescription());
        params.add(itemEntity.getUserId());
        return params;
    }

    @Override
    public ItemEntity findByUserId(Long id) {
        return findByField(id, "user_id");
    }

}
