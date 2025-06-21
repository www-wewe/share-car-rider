package cz.muni.fi.pv168.seminar01.delta.data.storage.dao;

import cz.muni.fi.pv168.seminar01.delta.error.StorageException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * Generic interface for CRUD operations on entities.
 *
 * @param <E> type of the entity this DAO operates on
 */
public interface DataAccessObject<E> {
    /**
     * Creates a new entity using the underlying data source.
     *
     * @param entity entity to be persisted
     * @return Entity instance with set PK
     * @throws IllegalArgumentException when the entity has already been persisted
     * @throws StorageException     when anything goes wrong with the underlying data source
     */
    E create(E entity);

    /**
     * Reads all entities from the underlying data source.
     *
     * @return collection of all entities known to the underlying data source
     * @throws StorageException when anything goes wrong with the underlying data source
     */
    Collection<E> findAll();

    /**
     * Finds entity by PK.
     *
     * @param pk entity PK
     * @return either empty if not found or the entity instance
     */
    Optional<E> findByPK(Object pk);

    /**
     * Updates an entity using the underlying data source.
     *
     * @param entity entity to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     * @throws StorageException     when anything goes wrong with the underlying data source
     */
    E update(E entity);

    /**
     * Deletes an entity using the underlying data source.
     *
     * @param entityPK entity PK to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     * @throws StorageException     when anything goes wrong with the underlying data source
     */
    void deleteByPK(Object entityPK);

    /**
     * Method for appending SQL WHERE clause
     *
     * @param sql StringBuilder object to be appended with WHERE clause
     * @param whereConstraints HashMap<String, Long> object, where String represents columnName in database and Long is value.
     *                        Will be converted to "WHERE `String` = Long AND `String` = Long AND ..."
     */
    default void addWhereClause(StringBuilder sql, HashMap<String, Long> whereConstraints) {
        if (whereConstraints == null) {
            return;
        }

        var first = true;
        for (var columnName : whereConstraints.keySet()) {
            if (!first) {
                sql.append(" AND");
            }
            sql.append(" `").append(columnName).append("` = ").append(whereConstraints.get(columnName));
            first = false;
        }
    }
}
