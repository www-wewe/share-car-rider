package cz.muni.fi.pv168.seminar01.delta.data.storage.entity;

/**
 * Represents the RideCategory entity
 */
public record RideCategoryEntity (
        Long id,
        Long rideId,
        Long categoryId
) {
    public RideCategoryEntity(long rideId, long categoryId) {
        this(null, rideId, categoryId);
    }
}
