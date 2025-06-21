package cz.muni.fi.pv168.seminar01.delta.data.storage.entity;

/**
 * Represents the Auto entity
 */
public record AutoEntity(
        Long id,
        String license_plate,
        String brand,
        Integer fuelTypeId
) {}
