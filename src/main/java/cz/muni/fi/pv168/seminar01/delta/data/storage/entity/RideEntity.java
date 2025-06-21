package cz.muni.fi.pv168.seminar01.delta.data.storage.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Represents the Ride entity
 */
public record RideEntity(
        Long id,
        String name,
        DestinationEntity fromDestinationEntity,
        DestinationEntity toDestinationEntity,
        Date date,
        int passengerCount,
        double distance,
        BigDecimal fuelPrice,
        List<CategoryEntity> categoryEntities,
        AutoEntity autoEntity
) {
    public RideEntity(String name, DestinationEntity fromDestinationEntity, DestinationEntity toDestinationEntity,
                      Date date, int passengerCount, double distance, BigDecimal fuelPrice,
                      List<CategoryEntity> categoryEntities, AutoEntity autoEntity) {
        this(null, name, fromDestinationEntity, toDestinationEntity, date,
                passengerCount, distance, fuelPrice, categoryEntities, autoEntity);
    }
}
