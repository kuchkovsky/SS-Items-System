package com.softserve.edu.dao;

import com.softserve.edu.db.ConnectionManager;
import com.softserve.edu.entity.IndexedEntity;
import com.softserve.edu.util.SqlProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractCrudDao<Entity extends IndexedEntity<Index>, Index extends Number>
        implements CrudDao<Entity, Index> {

    private static final Logger logger = Logger.getLogger(AbstractCrudDao.class);

    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private String entityProperty;

    public AbstractCrudDao(String entityProperty) {
        this.entityProperty = entityProperty;
        createTableIfNotExists();
    }

    @Override
    public Entity findById(Index id) {
        return findByField(id, "id");
    }

    @Override
    public List<Entity> findAll() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = SqlProperty.get(entityProperty + ".findAll");
            ResultSet resultSet = statement.executeQuery(query);
            List<Entity> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            logger.error("FindAll error", e);
            return null;
        }
    }

    @Override
    public void save(Entity element) {
        String query = (element.getId() == null) ?
                SqlProperty.get(entityProperty + ".saveNew") : SqlProperty.get(entityProperty + ".save");
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            fillEntity(preparedStatement, element);
            if (preparedStatement.executeUpdate() != 1) {
                logger.error("Save error: incorrect updated fields number");
            }
        } catch (SQLException e) {
            logger.error("Save error", e);
        }
    }

    @Override
    public void deleteById(Index id) {
       deleteByField(id, "id");
    }

    protected void deleteByField(Number index, String field) {
        String property = SqlProperty.get(entityProperty + ".deleteByField");
        String query = StringUtils.replace(property, "$field", field);
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            preparedStatement.setObject(1, index);
            if (preparedStatement.executeUpdate() != 1) {
                logger.error("DeleteByField error: incorrect updated fields number");
            }
        } catch (SQLException e) {
            logger.error("DeleteByField error", e);
        }
    }

    @Override
    public void delete(Entity element) {
        deleteById(element.getId());
    }

    @Override
    public void deleteAll() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = SqlProperty.get(entityProperty + ".deleteAll");
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("DeleteAll error", e);
        }
    }

    protected Entity findByField(Object object, String field) {
        String property = SqlProperty.get(entityProperty + ".findByField");
        String query = StringUtils.replace(property, "$field", field);
        try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query)) {
            preparedStatement.setObject(1, object);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createEntity(resultSet);
            }
        } catch (SQLException e) {
            logger.error("FindByField error", e);
        }
        return null;
    }

    protected abstract Entity createEntity(ResultSet resultSet) throws SQLException;

    protected abstract List<Object> getEntityParams(Entity entity);

    private void createTableIfNotExists() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {
            String query = SqlProperty.get(entityProperty + ".createTableIfNotExists");
            statement.execute(query);
        } catch (SQLException e) {
            logger.error("CreateTableIfNotExists error", e);
        }
    }

    private void fillEntity(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        List<Object> entityParams = getEntityParams(entity);
        boolean isNew = (entity.getId() == null);
        int offset = (isNew ? 1 : 0);
        for (int i = 0; i < entityParams.size() - offset; i++) {
            preparedStatement.setObject(i + 1, entityParams.get(i + offset));
        }
    }

}
