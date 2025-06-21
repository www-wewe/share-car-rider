package cz.muni.fi.pv168.seminar01.delta.gui.components;

/**
 * @author Andrej Gafrik
 *
 * Enum describing columns used in the table
 */

public enum Column {
    NAME("Jméno", "Name"),
    FROM("Odkud", "From"),
    TO("Kam", "To"),
    DATE("Datum", "Date"),
    PERSONS("Počet pasažérů", "Passenger count"),
    DISTANCE("Vzdálenost (km)", "Distance (km)"),
    PRICE("Cena paliva (KČ)", "Fuel price (KČ)"),
    CATEGORIES("Kategorie", "Category"),
    AUTO("Auto", "Car");

    public final String czech;
    public final String english;

    Column(String czech, String english) {
        this.czech = czech;
        this.english = english;
    }
}
