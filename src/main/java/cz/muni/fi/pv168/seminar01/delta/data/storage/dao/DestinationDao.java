package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.DestinationEntity;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * Class representing access point to database table 'destinations'
 *
 * @author Martin Vrzon
 */
public class DestinationDao implements DataAccessObject<DestinationEntity> {

    public static final String TABLE_NAME = "destinations";
    private final ConnectionHandler connectionHandler;

    public DestinationDao(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    private static DestinationEntity destinationFromResultSet(ResultSet resultSet) throws SQLException {
        return new DestinationEntity(resultSet.getLong("id"), resultSet.getString("name"));
    }

    public static void createTable(ConnectionHandler connectionHandler) {
        try {
            var statement = connectionHandler.use().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                            "`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT, " +
                            "`name` VARCHAR(255));");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError("Could not create table '" + TABLE_NAME + "'", e);
        }
    }

    @Override
    public DestinationEntity create(DestinationEntity entity) {
        var sql = "INSERT INTO " + TABLE_NAME + " (`name`) VALUES (?)";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.name());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long destinationId;

                if (keyResultSet.next()) {
                    destinationId = keyResultSet.getLong(1);
                } else {
                    throw new DatabaseError("Failed to fetch generated key for: " + entity, null);
                }
                if (keyResultSet.next()) {
                    throw new DatabaseError("Multiple keys returned for: " + entity, null);
                }

                return findByPK(destinationId).orElseThrow();
            }

        } catch (SQLException ex) {
            throw new DatabaseError("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<DestinationEntity> findAll() {
        var sql = "SELECT * FROM " + TABLE_NAME;
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            List<DestinationEntity> destinations = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var destination = destinationFromResultSet(resultSet);
                    destinations.add(destination);
                }
            }
            return destinations;
        } catch (SQLException e) {
            throw new DatabaseError("Failed to load all destinations", e);
        }
    }

    @Override
    public Optional<DestinationEntity> findByPK(Object id) {
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, (long) id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(destinationFromResultSet(resultSet));
            } else {
                // destination not found
                return Optional.empty();
            }
        } catch (
                SQLException ex) {
            throw new DatabaseError("Failed to load destination by id: " + id, ex);
        }
    }

    @Override
    public DestinationEntity update(DestinationEntity entity) {
        var sql = "UPDATE " + TABLE_NAME + " SET `name` = ? WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.name());
            statement.setLong(2, entity.id());

            if (statement.executeUpdate() == 0) {
                throw new DatabaseError("Failed to update non-existing destination: " + entity, null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to update destination with id: " + entity.id(), ex);
        }

        return findByPK(entity.id()).orElseThrow();
    }

    @Override
    public void deleteByPK(Object entityPK) {
        long pk = (long) entityPK;
        var sql = "DELETE FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, pk);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseError("Destination not found %s".formatted(pk), null);
            }
            if (rowsUpdated > 1) {
                throw new DatabaseError(
                        "More then 1 destination (rows=%d) has been deleted: %s".formatted(rowsUpdated, pk), null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete destination %s".formatted(pk), ex);
        }
    }
}
