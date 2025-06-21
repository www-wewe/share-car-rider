package cz.muni.fi.pv168.seminar01.delta.data.storage.mapper;

import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.CategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.RideEntity;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.sql.Date;
import java.util.ArrayList;


/**
 * Class for converting RideEntity <-> Ride
 *
 * @author Martin Vrzon
 */
public class RideMapper implements EntityModelMapper<RideEntity, Ride> {
    private final DestinationMapper destinationMapper = new DestinationMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final AutoMapper autoMapper = new AutoMapper();

    public RideMapper() {

    }

    @Override
    public RideEntity mapToEntity(Ride model) {
        var fromDestinationEntity = destinationMapper.mapToEntity(model.getFrom());
        var toDestinationEntity = destinationMapper.mapToEntity(model.getTo());
        var categoryEntities = new ArrayList<CategoryEntity>();
        for (var category : model.getCategories()) {
            categoryEntities.add(categoryMapper.mapToEntity(category));
        }
        var autoEntity = autoMapper.mapToEntity(model.getAuto());

        return new RideEntity(
                model.getId(),
                model.getName(),
                fromDestinationEntity,
                toDestinationEntity,
                Date.valueOf(model.getDate()),
                model.getPassengerCount(),
                model.getDistance(),
                model.getPrice(),
                categoryEntities,
                autoEntity
        );
    }

    @Override
    public Ride mapToModel(RideEntity entity) {
        var fromDestination = destinationMapper.mapToModel(entity.fromDestinationEntity());
        var toDestination = destinationMapper.mapToModel(entity.toDestinationEntity());
        var categories = new ArrayList<Category>();
        for (var categoryEntity : entity.categoryEntities()) {
            categories.add(categoryMapper.mapToModel(categoryEntity));
        }
        var auto = autoMapper.mapToModel(entity.autoEntity());

        return new Ride(
                entity.id(),
                entity.name(),
                fromDestination,
                toDestination,
                entity.date().toLocalDate(),
                entity.passengerCount(),
                entity.distance(),
                entity.fuelPrice(),
                categories,
                auto
        );
    }
}
