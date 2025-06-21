package cz.muni.fi.pv168.seminar01.delta;

import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekCalendar;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekChange;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeekCalendarTests {

    @Test
    void getTodayDateTest() {
        assertEquals(WeekCalendar.getTodayDate(), LocalDate.now());
    }

    @Test
    void setDateTest() {
        var weekCalendar = new WeekCalendar();
        var setDate = LocalDate.of(2022, 12, 24);
        weekCalendar.setCurrentDate(setDate);
        assertEquals(weekCalendar.getCurrentlySetDate(), setDate);
    }

    @Test
    void getWeekStartDateTest() {
        var weekCalendar = new WeekCalendar();
        var wednesday = LocalDate.of(2022, 10, 19);
        var monday = LocalDate.of(2022, 10, 17);
        weekCalendar.setCurrentDate(wednesday);
        assertEquals(weekCalendar.getWeekStartDate(weekCalendar.getCurrentlySetDate()), monday);
    }

    @Test
    void getWeekEndDateTest() {
        var weekCalendar = new WeekCalendar();
        var wednesday = LocalDate.of(2022, 10, 19);
        var friday = LocalDate.of(2022, 10, 23);
        weekCalendar.setCurrentDate(wednesday);
        assertEquals(weekCalendar.getWeekEndDate(weekCalendar.getCurrentlySetDate()), friday);
    }

    @Test
    void shiftWeekBeforeTest() {
        var weekCalendar = new WeekCalendar();
        var setDate = LocalDate.of(2022, 12, 24);
        var dateWeekBefore = LocalDate.of(2022, 12, 17);
        weekCalendar.setCurrentDate(setDate);
        weekCalendar.shiftWeek(WeekChange.PREV);
        assertEquals(weekCalendar.getCurrentlySetDate(), dateWeekBefore);
    }

    @Test
    void shiftWeekAfterTest() {
        var weekCalendar = new WeekCalendar();
        var setDate = LocalDate.of(2022, 12, 24);
        var dateWeekAfter = LocalDate.of(2022, 12, 31);
        weekCalendar.setCurrentDate(setDate);
        weekCalendar.shiftWeek(WeekChange.NEXT);
        assertEquals(weekCalendar.getCurrentlySetDate(), dateWeekAfter);
    }

    @Test
    void shiftWeekLeapYearTest() {
        //29.2.2020 is valid
        var weekCalendar = new WeekCalendar();
        var setDate = LocalDate.of(2020, 2, 22);
        var dateWeekAfter = LocalDate.of(2020, 2, 29);
        weekCalendar.setCurrentDate(setDate);
        weekCalendar.shiftWeek(WeekChange.NEXT);
        assertEquals(weekCalendar.getCurrentlySetDate(), dateWeekAfter);
    }
}
