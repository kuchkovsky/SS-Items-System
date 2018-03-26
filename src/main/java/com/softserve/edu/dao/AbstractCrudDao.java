package com.softserve.edu.dao;

import com.softserve.edu.db.ConnectionManager;
import com.softserve.edu.entity.IndexedEntity;
import com.softserve.edu.util.ApplicationContext;
import com.softserve.edu.util.SqlPropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractCrudDao<Entity extends IndexedEntity<Index>, Index extends Number>
        implements CrudDao<Entity, Index> {

    private static final Logger logger = Logger.getLogger(AbstractCrudDao.class);
    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final SqlPropertyReader sqlProperty = ApplicationContext.getInstance().getSqlPropertyReader();

    private String entityProperty;

    public AbstractCrudDao(String entityProperty) {
        this.entityProperty = entityProperty;
        createTableIfNotExists();
    }

    @Override
    public Entity findById(Index id) {
        return findOneByField(id, "id");
    }

    @Override
    public List<Entity> findAll() {
        List<Entity> entities = new ArrayList<>();
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = sqlProperty.get(entityProperty + ".findAll");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.error("DAO: findAll error", e);
        }
        return entities;
    }

    @Override
    public void save(Entity element) {
        String query = (element.getId() == null) ?
                sqlProperty.get(entityProperty + ".saveNew") : sqlProperty.get(entityProperty + ".save");
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            fillEntity(preparedStatement, element);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("DAO: save error", e);
        }
    }

    @Override
    public void deleteById(Index id) {
       deleteByField(id, "id");
    }

    protected void deleteByField(Number index, String fieldName) {
        String property = sqlProperty.get(entityProperty + ".deleteByField");
        String query = StringUtils.replace(property, "$field", fieldName);
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            preparedStatement.setObject(1, index);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("DAO: deleteByField error", e);
        }
    }

    @Override
    public void delete(Entity element) {
        deleteById(element.getId());
    }

    @Override
    public void deleteAll() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = sqlProperty.get(entityProperty + ".deleteAll");
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("DAO: deleteAll error", e);
        }
    }

    protected Entity findOneByField(Object parameter, String fieldName) {
        List<Entity> entities = findAllByField(parameter, fieldName);
        return entities.size() != 0 ? entities.get(0) : null;
    }

    protected List<Entity> findAllByField(Object parameter, String fieldName) {
        String property = sqlProperty.get(entityProperty + ".findByField");
        String query = StringUtils.replace(property, "$field", fieldName);
        List<Entity> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.error("DAO: findByField error", e);
        }
        return entities;
    }

    protected abstract Entity createEntity(ResultSet resultSet) throws SQLException;

    protected abstract List<Object> getEntityParams(Entity entity);

    private void createTableIfNotExists() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = sqlProperty.get(entityProperty + ".createTableIfNotExists");
            statement.execute(query);
        } catch (SQLException e) {
            logger.error("DAO: createTableIfNotExists error", e);
        }
    }

    private void fillEntity(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        List<Object> entityParams = getEntityParams(entity);
        for (int i = 1; i < entityParams.size(); i++) {
            preparedStatement.setObject(i, entityParams.get(i));
        }
        if (entity.getId() != null) {
            preparedStatement.setObject(entityParams.size(), entityParams.get(0));
        }
    }

}
