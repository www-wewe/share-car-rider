package cz.muni.fi.pv168.seminar01.delta.gui.components;

import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecordTableModel extends AbstractTableModel {

    private final List<Ride> records;

    private final List<String> columns;

    public RecordTableModel(Collection<Ride> records) {
        this.records = new ArrayList<>(records);
        columns = initColumnNames();
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var record = getEntity(rowIndex);
        Column column = Column.values()[columnIndex];
        return switch (column) {
            case NAME -> record.getName();
            case FROM -> record.getFrom();
            case TO -> record.getTo();
            case DATE -> record.getDate();
            case PERSONS -> record.getPassengerCount();
            case DISTANCE -> record.getDistance();
            case PRICE -> record.getPrice();
            case CATEGORIES -> record.getCategories();
            case AUTO -> record.getAuto();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }

    public Ride getRow(int index) {
        return records.get(index);
    }

    public int getColumnIndex(Column column) {
        return this.columns.indexOf(column.czech);
    }

    private Ride getEntity(int rowIndex) {
        return records.get(rowIndex);
    }

    private List<String> initColumnNames() {
        final List<String> columns;
        columns = new ArrayList<>();
        for (Column c : Column.values()) {
            columns.add(c.czech);
        }
        return columns;
    }

}
