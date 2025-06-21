package cz.muni.fi.pv168.seminar01.delta.data.storage.mapper;

/**
 * Map from one entity to another
 * We are using this mappers map between the business models and database entities
 *
 * @param <E> Type of DTO (database entity)
 * @param <M> Type of the Entity (business entity)
 */
public interface EntityModelMapper<E, M> {
    E mapToEntity(M model);

    M mapToModel(E entity);
}
