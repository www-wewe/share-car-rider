package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.RideCategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * Class representing access point to database table 'ride_categories'
 *
 * @author Martin Vrzon
 */
public class RideCategoryDao implements DataAccessObject<RideCategoryEntity> {

    public static final String TABLE_NAME = "ride_categories";
    private final ConnectionHandler connectionHandler;

    public RideCategoryDao(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    private static RideCategoryEntity rideCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new RideCategoryEntity(resultSet.getLong("id"), resultSet.getLong("ride_id"),
                resultSet.getLong("category_id"));
    }

    public static void createTable(ConnectionHandler connectionHandler) {
        try {
            var statement = connectionHandler.use().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                            "`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT, " +
                            "`ride_id` BIGINT UNSIGNED, " +
                            "`category_id` BIGINT UNSIGNED," +
                            "FOREIGN KEY (`ride_id`) REFERENCES " + RideDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE, " +
                            "FOREIGN KEY (`category_id`) REFERENCES " + CategoryDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE);");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError("Could not create table '" + TABLE_NAME + "'", e);
        }
    }

    @Override
    public RideCategoryEntity create(RideCategoryEntity entity) {
        var sql = "INSERT INTO " + TABLE_NAME + " (`ride_id`, `category_id`) VALUES (?, ?)";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.rideId());
            statement.setLong(2, entity.categoryId());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long rideCategoryId;

                if (keyResultSet.next()) {
                    rideCategoryId = keyResultSet.getLong(1);
                } else {
                    throw new DatabaseError("Failed to fetch generated key for: " + entity, null);
                }
                if (keyResultSet.next()) {
                    throw new DatabaseError("Multiple keys returned for: " + entity, null);
                }
                return findByPK(rideCategoryId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<RideCategoryEntity> findAll() {
        var sql = "SELECT * FROM " + TABLE_NAME;
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            List<RideCategoryEntity> rideCategories = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var rideCategory = rideCategoryFromResultSet(resultSet);
                    rideCategories.add(rideCategory);
                }
            }

            return rideCategories;
        } catch (SQLException e) {
            throw new DatabaseError("Failed to load all rideCategories", e);
        }
    }

    public Collection<RideCategoryEntity> findAllWhere(HashMap<String, Long> whereConstraints) {
        var sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE");
        var connection = connectionHandler.use();

        try (var statement = connection.createStatement()) {
            addWhereClause(sql, whereConstraints);
            List<RideCategoryEntity> rideCategories = new ArrayList<>();
            try (var resultSet = statement.executeQuery(sql.toString())) {
                while (resultSet.next()) {
                    var rideCategory = rideCategoryFromResultSet(resultSet);
                    rideCategories.add(rideCategory);
                }
            }

            return rideCategories;
        } catch (SQLException e) {
            throw new DatabaseError("Failed to load all rideCategories", e);
        }
    }

    @Override
    public Optional<RideCategoryEntity> findByPK(Object id) {
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = ?";
        Connection connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, (long) id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rideCategoryFromResultSet(resultSet));
            } else {
                // rideCategory not found
                return Optional.empty();
            }
        } catch (
                SQLException ex) {
            throw new DatabaseError("Failed to load rideCategory by id: " + id, ex);
        }
    }

    @Override
    public RideCategoryEntity update(RideCategoryEntity entity) {
        var sql = "UPDATE " + TABLE_NAME +
                " SET `ride_id` = ?, `category_id` = ? WHERE `id` = ?";
        Connection connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, entity.rideId());
            statement.setLong(2, entity.categoryId());
            statement.setLong(3, entity.id());

            if (statement.executeUpdate() == 0) {
                throw new DatabaseError("Failed to update non-existing rideCategory: " + entity, null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to update rideCategory with id: " + entity.id(), ex);
        }

        return findByPK(entity.id()).orElseThrow();
    }

    public void deleteAllWhere(HashMap<String, Long> whereConstraints) {
        var sql = new StringBuilder("DELETE FROM " + TABLE_NAME + " WHERE");
        Connection connection = connectionHandler.use();

        try (var statement = connection.createStatement()) {
            addWhereClause(sql, whereConstraints);
            statement.executeUpdate(sql.toString());
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete rideCategories", ex);
        }
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
                throw new DatabaseError("rideCategory not found %s".formatted(pk), null);
            }
            if (rowsUpdated > 1) {
                throw new DatabaseError(
                        "More then 1 rideCategory (rows=%d) has been deleted: %s".formatted(rowsUpdated, pk), null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete rideCategory %s".formatted(pk), ex);
        }
    }
}

