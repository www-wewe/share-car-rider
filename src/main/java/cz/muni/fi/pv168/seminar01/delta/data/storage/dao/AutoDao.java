package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.AutoEntity;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * Class representing access point to database table 'autos'
 *
 * @author Martin Vrzon
 */
public class AutoDao implements DataAccessObject<AutoEntity> {

    public static final String TABLE_NAME = "autos";
    private final ConnectionHandler connectionHandler;

    public AutoDao(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    private static AutoEntity autoFromResultSet(ResultSet resultSet) throws SQLException {
        return new AutoEntity(resultSet.getLong("id"), resultSet.getString("license_plate"),
                resultSet.getString("brand"), (resultSet.getInt("fuel_type_id")));
    }

    public static void createTable(ConnectionHandler connectionHandler) {
        try {
            var statement = connectionHandler.use().prepareStatement
                    ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                            "`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT, " +
                            "`license_plate` CHAR(7), " +
                            "`brand` VARCHAR(255), " +
                            "`fuel_type_id` TINYINT UNSIGNED);");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError("Could not create table '" + TABLE_NAME + "'", e);
        }
    }

    @Override
    public AutoEntity create(AutoEntity entity) {
        var sql = "INSERT INTO " + TABLE_NAME + " (`license_plate`, `brand`, `fuel_type_id`) VALUES (?, ?, ?)";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.license_plate());
            statement.setString(2, entity.brand());
            statement.setLong(3, entity.fuelTypeId());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long autoId;

                if (keyResultSet.next()) {
                    autoId = keyResultSet.getLong(1);
                } else {
                    throw new DatabaseError("Failed to fetch generated key for auto with id=" + entity, null);
                }
                if (keyResultSet.next()) {
                    throw new DatabaseError("Multiple keys returned for auto with id=" + entity, null);
                }

                return findByPK(autoId).orElseThrow();
            }

        } catch (SQLException ex) {
            throw new DatabaseError("Failed to store auto with id=" + entity, ex);
        }
    }

    @Override
    public Collection<AutoEntity> findAll() {
        var sql = "SELECT * FROM " + TABLE_NAME;
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            List<AutoEntity> autos = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var auto = autoFromResultSet(resultSet);
                    autos.add(auto);
                }
            }
            return autos;
        } catch (SQLException e) {
            throw new DatabaseError("Failed to load all autos", e);
        }
    }

    @Override
    public Optional<AutoEntity> findByPK(Object id) {
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, (long) id);

            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(autoFromResultSet(resultSet));
            } else {
                // auto not found
                return Optional.empty();
            }
        } catch (
                SQLException ex) {
            throw new DatabaseError("Failed to load auto by id: " + id, ex);
        }
    }

    @Override
    public AutoEntity update(AutoEntity entity) {
        var sql = "UPDATE " + TABLE_NAME +
                " SET `license_plate` = ?, `brand` = ?, `fuel_type_id` = ? WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.license_plate());
            statement.setString(2, entity.brand());
            statement.setLong(3, entity.fuelTypeId());
            statement.setLong(4, entity.id());

            if (statement.executeUpdate() == 0) {
                throw new DatabaseError("Failed to update non-existing auto: id=" + entity.id(), null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to update auto with id=" + entity.id(), ex);
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
                throw new DatabaseError("Auto with id=%d not found".formatted(pk), null);
            }
            if (rowsUpdated > 1) {
                throw new DatabaseError(
                        "More then 1 auto (rows=%d) has been deleted: id=%d".formatted(rowsUpdated, pk), null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete auto with id=%d".formatted(pk), ex);
        }
    }
}
