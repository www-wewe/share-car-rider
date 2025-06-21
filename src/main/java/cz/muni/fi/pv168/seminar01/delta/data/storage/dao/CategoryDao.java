package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.CategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * Class representing access point to database table 'categories'
 *
 * @author Martin Vrzon
 */
public class CategoryDao implements DataAccessObject<CategoryEntity> {

    public static final String TABLE_NAME = "categories";
    private final ConnectionHandler connectionHandler;

    public CategoryDao(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    private static CategoryEntity categoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new CategoryEntity(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("parent_id"));
    }

    public static void createTable(ConnectionHandler connectionHandler) {
        try {
            var statement = connectionHandler.use().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                            "`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT, " +
                            "`name` VARCHAR(255)," +
                            "`parent_id` BIGINT UNSIGNED," +
                            "FOREIGN KEY (`parent_id`) REFERENCES " + CategoryDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE);"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError("Could not create table '" + TABLE_NAME + "'", e);
        }
    }

    @Override
    public CategoryEntity create(CategoryEntity entity) {
        var sql = "INSERT INTO " + TABLE_NAME + " (`name`, `parent_id`) VALUES (?, ?)";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.name());
            if (entity.parentId() == null)
                statement.setNull(2, java.sql.Types.NULL);
            else
                statement.setLong(2, entity.parentId());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long categoryId;

                if (keyResultSet.next()) {
                    categoryId = keyResultSet.getLong(1);
                } else {
                    throw new DatabaseError("Failed to fetch generated key for: " + entity, null);
                }
                if (keyResultSet.next()) {
                    throw new DatabaseError("Multiple keys returned for: " + entity, null);
                }

                return findByPK(categoryId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        var sql = "SELECT * FROM " + TABLE_NAME;
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            List<CategoryEntity> categories = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CategoryEntity category = categoryFromResultSet(resultSet);
                    categories.add(category);
                }
            }

            return categories;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseError("Failed to load all categories", e);
        }
    }

    @Override
    public Optional<CategoryEntity> findByPK(Object id) {
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, (long) id);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(categoryFromResultSet(resultSet));
            } else {
                // category not found
                return Optional.empty();
            }
        } catch (
                SQLException ex) {
            throw new DatabaseError("Failed to load category by id: " + id, ex);
        }
    }

    @Override
    public CategoryEntity update(CategoryEntity entity) {
        var sql = "UPDATE " + TABLE_NAME + " SET `name` = ? WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.name());
            statement.setLong(2, entity.id());

            if (statement.executeUpdate() == 0) {
                throw new DatabaseError("Failed to update non-existing category: " + entity, null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to update category with id: " + entity.id(), ex);
        }

        return findByPK(entity.id()).orElseThrow();
    }

    @Override
    public void deleteByPK(Object entityPK) {
        var pk = (long) entityPK;
        var sql = "DELETE FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, pk);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseError("Category not found %s".formatted(pk), null);
            }
            if (rowsUpdated > 1) {
                throw new DatabaseError(
                        "More then 1 category (rows=%d) has been deleted: %s".formatted(rowsUpdated, pk), null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete category %s".formatted(pk), ex);
        }
    }
}
