package cz.muni.fi.pv168.seminar01.delta.gui.components;

/**
 * Enum determining week change.
 *
 * @author Martin Vrzon
 */
public enum WeekChange {
    PREV(-7),
    NEXT(7);

    public final int dayShift;

    WeekChange(int dayShift) {
        this.dayShift = dayShift;
    }
}
