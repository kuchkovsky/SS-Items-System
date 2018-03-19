package com.softserve.edu.dao;

import java.util.List;

public interface CrudDao<Entity, Index> {

    Entity findById(Index id);

    List<Entity> findAll();

    void save(Entity entity);

    void deleteById(Index id);

    void delete(Entity element);

    void deleteAll();

}
