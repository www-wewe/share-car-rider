package cz.muni.fi.pv168.seminar01.delta.gui.category;

/**
 * Enum describing type of nodes in tree component
 *
 * @author Veronika Lenkova
 */
public enum TreeModelType {
    CATEGORY("Kategorie"),
    DESTINATION("Destinace"),
    CAR("Auta");

    public final String label;

    TreeModelType(String label) {
        this.label = label;
    }
}
