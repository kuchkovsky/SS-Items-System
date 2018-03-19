package com.softserve.edu.dao;

import com.softserve.edu.entity.ItemEntity;

public interface ItemDao extends CrudDao<ItemEntity, Long> {

    ItemEntity findByUserId(Long id);

}
