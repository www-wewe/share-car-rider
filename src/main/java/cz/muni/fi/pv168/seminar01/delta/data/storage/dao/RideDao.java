package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.error.StorageException;
import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.AutoEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.CategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.DestinationEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.RideCategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.RideEntity;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * Class representing access point to database table 'rides'
 *
 * @author Martin Vrzon
 */
public class RideDao implements DataAccessObject<RideEntity> {

    public static final String TABLE_NAME = "rides";
    private final ConnectionHandler connectionHandler;
    private final DestinationDao destinationDao;
    private final RideCategoryDao rideCategoryDao;
    private final CategoryDao categoryDao;
    private final AutoDao autoDao;

    public RideDao(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        this.destinationDao = new DestinationDao(connectionHandler);
        this.rideCategoryDao = new RideCategoryDao(connectionHandler);
        this.categoryDao = new CategoryDao(connectionHandler);
        this.autoDao = new AutoDao(connectionHandler);
    }

    private static RideEntity rideFromResultSet(
            ResultSet resultSet, DestinationEntity fromDestinationEntity, DestinationEntity toDestinationEntity,
            List<CategoryEntity> rideCategoryEntities, AutoEntity autoEntity) throws SQLException {
        return new RideEntity(
                resultSet.getLong("id"), resultSet.getString("name"), fromDestinationEntity,
                toDestinationEntity, resultSet.getDate("date"), resultSet.getInt("passenger_count"),
                resultSet.getDouble("distance"), resultSet.getBigDecimal("fuel_price"),
                rideCategoryEntities, autoEntity);
    }

    private static List<CategoryEntity> getRideCategories(RideCategoryDao rideCategoryDao, CategoryDao categoryDao,
                                                          HashMap<String, Long> whereConstraint) {

        var rideCategoryEntities = rideCategoryDao.findAllWhere(whereConstraint);
        var categoryEntities = new ArrayList<CategoryEntity>();

        for (var rideCategoryEntity : rideCategoryEntities) {
            var categoryEntity = categoryDao.findByPK(rideCategoryEntity.categoryId());
            categoryEntity.ifPresent(categoryEntities::add);
        }

        return categoryEntities;
    }

    @Override
    public RideEntity create(RideEntity entity) {
        var sql = "INSERT INTO " + TABLE_NAME + " (" +
                "`name`, `from_dest_id`, `to_dest_id`, `date`, `passenger_count`, `distance`," +
                " `fuel_price`, `auto_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        var connection = connectionHandler.use();
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            setStatement(statement, entity);
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long rideId;

                if (keyResultSet.next()) {
                    rideId = keyResultSet.getLong(1);
                } else {
                    throw new DatabaseError("Failed to fetch generated key for: " + entity, null);
                }
                if (keyResultSet.next()) {
                    throw new DatabaseError("Multiple keys returned for: " + entity, null);
                }

                for (var categoryEntity : entity.categoryEntities()) {
                    rideCategoryDao.create(new RideCategoryEntity(rideId, categoryEntity.id()));
                }

                return findByPK(rideId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to store: " + entity, ex);
        }
    }

    private void setStatement(PreparedStatement statement, RideEntity entity) throws SQLException {
        statement.setString(1, entity.name());
        statement.setLong(2, entity.fromDestinationEntity().id());
        statement.setLong(3, entity.toDestinationEntity().id());
        statement.setDate(4, entity.date());
        statement.setInt(5, entity.passengerCount());
        statement.setDouble(6, entity.distance());
        statement.setDouble(7, entity.fuelPrice().doubleValue());
        statement.setLong(8, entity.autoEntity().id());
    }

    @Override
    public Collection<RideEntity> findAll() {
        var sql = "SELECT * FROM " + TABLE_NAME;
        return execute(sql, "Failed to load all rides");
    }

    /**
     * Finds all ride entities with date in specified range
     *
     * @param startDate range start date
     * @param endDate range end date
     * @return Collection<RideEntity> object containing all found ride entities
     */
    public Collection<RideEntity> findAllInRange(Date startDate, Date endDate) {
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `date` >= '" + startDate + "' AND `date` <= '" + endDate +"';";
        return execute(sql, "Failed to load all rides in range");
    }

    /**
     * Executes provided SQL command and returns result from database
     *
     * @param sql SQL command to execute
     * @param errorMessage message to be passed to DatabaseError exception in case of error
     * @return Collection<RideEntity> object containing ride entities from result
     */
    private Collection<RideEntity> execute(String sql, String errorMessage) {

        var connection = connectionHandler.use();
        try (var statement = connection.prepareStatement(sql)){

            List<RideEntity> rideEntities = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {

                var whereConstraint = new HashMap<String, Long>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    whereConstraint.put("ride_id", id);

                    var fromDestinationEntity = destinationDao.findByPK(resultSet.getLong("from_dest_id")).orElseThrow();
                    var toDestinationEntity = destinationDao.findByPK(resultSet.getLong("to_dest_id")).orElseThrow();
                    var autoEntity = autoDao.findByPK(resultSet.getLong("auto_id")).orElseThrow();
                    var categoryEntities = getRideCategories(rideCategoryDao, categoryDao, whereConstraint);

                    if (categoryEntities.size() == 0) {
                        deleteByPK(id);
                        continue;
                    }

                    var rideEntity = rideFromResultSet(resultSet, fromDestinationEntity, toDestinationEntity, categoryEntities, autoEntity);
                    rideEntities.add(rideEntity);
                }
            }
            return rideEntities;
        } catch (SQLException e) {
            throw new DatabaseError(errorMessage, e);
        }
    }

    @Override
    public Optional<RideEntity> findByPK(Object id) {
        var rideId = (long) id;
        var sql = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = " + rideId;
        var rideEntities = execute(sql, "Failed to load ride by id: " + rideId);

        if (rideEntities.size() > 1) {
            throw new StorageException("Ambiguous rides with id: " + rideId);
        }

        for (var rideEntity : rideEntities) {
            return Optional.of(rideEntity);
        }
        return Optional.empty();
    }

    @Override
    public RideEntity update(RideEntity entity) {
        var connection = connectionHandler.use();
        var sql = "UPDATE " + TABLE_NAME + " SET `name` = ?," +
                " `from_dest_id` = ?, `to_dest_id` = ?, `date` = ?, `passenger_count` = ?," +
                " `distance` = ?, `fuel_price` = ?, `auto_id` = ? WHERE `id` = ?";
        try (var statement = connection.prepareStatement(sql)){
            setStatement(statement, entity);
            statement.setLong(9, entity.id());

            var whereConstraint = new HashMap<String, Long>();
            whereConstraint.put("ride_id", entity.id());
            rideCategoryDao.deleteAllWhere(whereConstraint);

            for (var categoryEntity : entity.categoryEntities()) {
                rideCategoryDao.create(new RideCategoryEntity(entity.id(), categoryEntity.id()));
            }

            if (statement.executeUpdate() == 0) {
                throw new DatabaseError("Failed to update non-existing ride: " + entity, null);
            }
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to update ride with id: " + entity.id(), ex);
        }

        return findByPK(entity.id()).orElseThrow();
    }

    @Override
    public void deleteByPK(Object entityPK) {
        var pk = (Long) entityPK;
        var sql = "DELETE FROM " + TABLE_NAME + " WHERE `id` = ?";
        var connection = connectionHandler.use();

        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, pk);
            var whereConstraint = new HashMap<String, Long>();
            whereConstraint.put("ride_id", pk);
            rideCategoryDao.deleteAllWhere(whereConstraint);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseError("Failed to delete ride %s".formatted(pk), ex);
        }
    }

    public static void createTable(ConnectionHandler connectionHandler) {
        var connection = connectionHandler.use();
        var sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT, " +
                "`name` VARCHAR(255), " +
                "`from_dest_id` BIGINT UNSIGNED, " +
                "`to_dest_id` BIGINT UNSIGNED, " +
                "`date` DATE, " +
                "`passenger_count` INT UNSIGNED, " +
                "`distance` DOUBLE, " +
                "`fuel_price` DOUBLE, " +
                "`auto_id` BIGINT UNSIGNED, " +
                "FOREIGN KEY (`from_dest_id`) REFERENCES " + DestinationDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (`to_dest_id`) REFERENCES " + DestinationDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (`auto_id`) REFERENCES " + AutoDao.TABLE_NAME + "(`id`) ON UPDATE CASCADE ON DELETE CASCADE);";
        try (var statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError("Could not create table '" + TABLE_NAME + "'", e);
        }
    }
}
