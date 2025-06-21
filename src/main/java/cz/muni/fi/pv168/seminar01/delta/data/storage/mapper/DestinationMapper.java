package cz.muni.fi.pv168.seminar01.delta.data.storage.mapper;

import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.DestinationEntity;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;


/**
 * Class for converting DestinationEntity <-> Destination
 *
 * @author Martin Vrzon
 */
public class DestinationMapper implements EntityModelMapper<DestinationEntity, Destination> {

    @Override
    public DestinationEntity mapToEntity(Destination model) {
        return new DestinationEntity(model.getId(), model.getName());
    }

    @Override
    public Destination mapToModel(DestinationEntity entity) {
        return new Destination(entity.id(), entity.name());
    }
}
