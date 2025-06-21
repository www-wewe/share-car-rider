package cz.muni.fi.pv168.seminar01.delta.gui.components;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.Filters;
import cz.muni.fi.pv168.seminar01.delta.gui.mainwindow.Displayed;
import cz.muni.fi.pv168.seminar01.delta.gui.mainwindow.ShowRides;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.RideRepository;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.Collection;

/**
 * Class grouping Swing elements on the middle-right of Share_Car_Rider application window.
 * Contains methods for working with these elements.
 *
 * @author Martin Vrzon
 */
public class TableComponent {

    private final WeekCalendar weekCalendar;
    private final JTable table;
    private final JLabel weekLabel;
    private final RideRepository rides;
    private final Filters filters;
    private final ShowRides showRides;

    public TableComponent(JTable dataTable, JLabel weekLabel, Repository<Ride> rides, Filters filters, ShowRides showRides) {
        this.weekCalendar = new WeekCalendar();
        this.table = dataTable;
        this.weekLabel = weekLabel;
        this.rides = (RideRepository) rides;
        this.filters = filters;
        this.showRides = showRides;

        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshTableData();

        TableColumnModel columns = table.getColumnModel();

        columns.getColumn(Column.NAME.ordinal()).setMinWidth(200);
        columns.getColumn(Column.PERSONS.ordinal()).setMinWidth(150);
    }

    public void refreshTableData() {
        if (showRides.getState() == Displayed.ALL || showRides.getState() == Displayed.SEARCH) {
            showAll();
        } else {
            var currentDate = weekCalendar.getCurrentlySetDate();
            var currentWeekStartDate = WeekCalendar.getWeekStartDate(currentDate);
            var currentWeekEndDate = WeekCalendar.getWeekEndDate(currentDate);
            var model = new RecordTableModel(rides.fetchAllInRange(currentWeekStartDate, currentWeekEndDate));
            table.setModel(model);
            weekLabel.setText(WeekCalendar.formatDate(currentWeekStartDate) + " - " + WeekCalendar.formatDate(currentWeekEndDate));
            filters.applyFilters();
        }
    }

    private void showAll() {
        var model = new RecordTableModel(rides.findAll());
        table.setModel(model);
        filters.applyFilters();
    }


    public void refreshTableDataWithFetch(){
        rides.refresh();
        refreshTableData();
    }

    public RecordTableModel getModel() {
        return (RecordTableModel) table.getModel();
    }

    public int getSelectedRow() {
        return table.convertRowIndexToModel(table.getSelectedRow());
    }

    public Ride getSelectedRecord() {
        var model = (RecordTableModel) table.getModel();
        return model.getRow(getSelectedRow());
    }

    public void shiftWeek(WeekChange weekChange) { weekCalendar.shiftWeek(weekChange); }

    public void add(Ride record) {
        this.rides.create(record);
    }

    public void delete(int index) {
        this.rides.deleteByIndex(index);
    }

    public void update(Ride record) {
        this.rides.update(record);
    }
}
