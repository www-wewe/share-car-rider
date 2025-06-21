package cz.muni.fi.pv168.seminar01.delta.gui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class managing weeks in TableComponent.
 * Provides methods for getting, setting, parsing and formatting dates with focus on week.
 *
 * @author Martin Vrzon
 */
public class WeekCalendar {

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    private LocalDate currentDate = LocalDate.now();

    public static String formatDate(LocalDate date) {
        return dateFormatter.format(date);
    }

    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    public static LocalDate getCurrentWeekStart() {
        return getWeekStartDate(LocalDate.now());
    }

    public static LocalDate getCurrentWeekEnd() {
        return getWeekEndDate(LocalDate.now());
    }

    public static boolean isDateInRange(LocalDate date, LocalDate rangeStart, LocalDate rangeEnd) {
        return (rangeStart.isEqual(date) || rangeStart.isBefore(date))
                && (date.isEqual(rangeEnd) || date.isBefore(rangeEnd));
    }

    public static LocalDate getWeekStartDate(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - 1);
    }

    public static LocalDate getWeekEndDate(LocalDate date) {
        return date.plusDays(7 - date.getDayOfWeek().getValue());
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public LocalDate getCurrentlySetDate() {
        return currentDate;
    }

    public void shiftWeek(WeekChange weekChange) {
        currentDate = currentDate.plusDays(weekChange.dayShift);
    }
}
