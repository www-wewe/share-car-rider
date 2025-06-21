package cz.muni.fi.pv168.seminar01.delta.data.processing;

import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;
import cz.muni.fi.pv168.seminar01.delta.gui.components.RecordTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.Objects;

public class FilterTools {


    private final JTable table;
    private final int passengersFrom;
    private final int passengersTo;
    private final double rideLengthFrom;
    private final double rideLengthTo;
    private final String destinationFrom;
    private final String destinationTo;
    private final String carLicencePlate;
    private final String searchWord;

    public FilterTools(JTable table, int passengersFrom, int passengersTo,
                double rideLengthFrom, double rideLengthTo, String destinationFrom,
                String destinationTo, String carLicencePlate, String searchWord) {
        this.table = table;
        this.passengersFrom = passengersFrom;
        this.passengersTo = passengersTo;
        this.rideLengthFrom = rideLengthFrom;
        this.rideLengthTo = rideLengthTo;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.carLicencePlate = carLicencePlate;
        this.searchWord = searchWord;

    }

    public void filterTable() {
        RecordTableModel model = (RecordTableModel) table.getModel();

        TableRowSorter<RecordTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        RowFilter<Object,Object> filter = new RowFilter<>() {
            public boolean include(Entry<?, ?> entry) {
                return testPassengers(entry, model) &&
                        testRideLength(entry, model) &&
                        testDestinationFrom(entry, model) &&
                        testDestinationTo(entry, model) &&
                        testCar(entry, model) &&
                        testSearchWord(entry, model);
            }
        };
        sorter.setRowFilter(filter);
    }

    private boolean testPassengers(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int passengerIndex = model.getColumnIndex(Column.PERSONS);
        int numberOfPassenger = Integer.parseInt(entry.getStringValue(passengerIndex));
        if (numberOfPassenger < passengersFrom) {
            return false;
        } else return passengersTo <= 0 || numberOfPassenger <= passengersTo;
    }

    private boolean testRideLength(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int rideLengthIndex = model.getColumnIndex(Column.DISTANCE);
        double rideLength = Double.parseDouble(entry.getStringValue(rideLengthIndex));
        if (rideLength < rideLengthFrom) {
            return false;
        } else return rideLengthTo <= 0 || rideLength <= rideLengthTo;
    }

    private boolean testDestinationFrom(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int fromIndex = model.getColumnIndex(Column.FROM);
        return destinationFrom.isEmpty()
                || Objects.equals(entry.getStringValue(fromIndex), destinationFrom);
    }

    private boolean testDestinationTo(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int toIndex = model.getColumnIndex(Column.TO);
        return destinationTo.isEmpty()
                || Objects.equals(entry.getStringValue(toIndex), destinationTo);
    }

    private boolean testCar(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int carIndex = model.getColumnIndex(Column.AUTO);
        return carLicencePlate.isEmpty()
                || Objects.equals(entry.getStringValue(carIndex), carLicencePlate);
    }

    private boolean testSearchWord(RowFilter.Entry<?, ?> entry, RecordTableModel model) {
        int nameIndex = model.getColumnIndex(Column.NAME);
        String rideName = entry.getStringValue(nameIndex);
        return searchWord.isEmpty() || rideName.contains(searchWord);
    }
}
