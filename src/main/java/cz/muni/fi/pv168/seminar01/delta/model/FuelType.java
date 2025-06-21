package cz.muni.fi.pv168.seminar01.delta.model;

/**
 * Enum class of fuel types
 * @author Veronika Lenkova
 */
public enum FuelType {
    DIESEL("diesel"),
    PETROL("petrol"),
    ELECTRIC("electric");

    public final String label;

    FuelType(String label) {
        this.label = label;
    }
}
