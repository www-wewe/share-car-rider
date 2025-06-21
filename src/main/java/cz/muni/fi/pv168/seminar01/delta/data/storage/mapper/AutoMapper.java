package cz.muni.fi.pv168.seminar01.delta.data.storage.mapper;

import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.AutoEntity;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.FuelType;


/**
 * Class for converting AutoEntity <-> Auto
 *
 * @author Martin Vrzon
 */
public class AutoMapper implements EntityModelMapper<AutoEntity, Auto> {
    @Override
    public AutoEntity mapToEntity(Auto model) {
        return new AutoEntity(model.getId(), model.getLicensePlate(), model.getBrand(), model.getFuelType().ordinal());
    }

    @Override
    public Auto mapToModel(AutoEntity entity) {
        return new Auto(entity.id(), entity.license_plate(), entity.brand(), FuelType.values()[entity.fuelTypeId()]);
    }
}
