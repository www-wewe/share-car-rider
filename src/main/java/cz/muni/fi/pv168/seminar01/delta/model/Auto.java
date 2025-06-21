package cz.muni.fi.pv168.seminar01.delta.model;

import java.util.Objects;

/**
 * Class representing Auto
 * @author Veronika Lenkova
 */
public class Auto {
    private Long id;
    private String licensePlate;
    private String brand;
    private FuelType fuelType;

    public Auto(Long id, String licensePlate, String brand, FuelType fuelType) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.fuelType = fuelType;
    }

    public Auto(String licensePlate, String brand, FuelType fuelType) {
        this(null, licensePlate, brand, fuelType);
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return getLicensePlate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return Objects.equals(id, auto.id) && Objects.equals(licensePlate, auto.licensePlate) && Objects.equals(brand, auto.brand) && fuelType == auto.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, brand, fuelType);
    }
}
