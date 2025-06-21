package cz.muni.fi.pv168.seminar01.delta;

import cz.muni.fi.pv168.seminar01.delta.data.processing.FilterTools;
import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;
import cz.muni.fi.pv168.seminar01.delta.gui.components.RecordTableModel;
import cz.muni.fi.pv168.seminar01.delta.model.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterToolsTest {
    private static final List<Category> SHOPPING = List.of(new Category("Shopping"));
    private static final LocalDate DATE = LocalDate.of(2022, 11, 2);

    private final List<Ride> rides1;
    private List<Ride> rides2;
    private List<Ride> rides3;

    private static final Auto FIAT = new Auto("abc123", "Fiat", FuelType.DIESEL);
    private static final Auto VOLVO = new Auto("abc124", "Volvo", FuelType.PETROL);
    private static final Auto OPEL = new Auto("abc124", "Opel", FuelType.PETROL);
    private static final Auto BMW = new Auto("abc126", "BMW", FuelType.DIESEL);

    private static final Destination SHOP = new Destination("Shop");
    private static final Destination CASTLE = new Destination("Castle");
    private static final Destination HOME = new Destination("Home");

    FilterToolsTest() {
        this.rides1 = new ArrayList<>();

        initRides1();
        //intiRides2();
        //initRides3();
    }

    private void initRides1() {
        rides1.add(new Ride("One",HOME, SHOP, DATE, 1, 5.49, new BigDecimal(15), SHOPPING, FIAT));
        rides1.add(new Ride("Two",HOME, SHOP, DATE, 2, 10, new BigDecimal(30), SHOPPING, VOLVO));
        rides1.add(new Ride("Three",HOME, CASTLE, DATE, 3, 15.1, new BigDecimal(20), SHOPPING, FIAT));
        rides1.add(new Ride("Four",HOME, CASTLE, DATE, 4, 20, new BigDecimal(25), SHOPPING, OPEL));
        rides1.add(new Ride("Five",CASTLE, CASTLE, DATE, 5, 30, new BigDecimal(45), SHOPPING, OPEL));
        rides1.add(new Ride("Six",CASTLE, HOME, DATE, 6, 30, new BigDecimal(45), SHOPPING, VOLVO));

        rides1.add(new Ride("AAsevenAA",HOME, SHOP, DATE, 1, 5.49, new BigDecimal(15), SHOPPING, FIAT));
        rides1.add(new Ride("EightAAA",HOME, SHOP, DATE, 2, 10, new BigDecimal(30), SHOPPING, VOLVO));
        rides1.add(new Ride("AAANine",HOME, CASTLE, DATE, 3, 15.1, new BigDecimal(20), SHOPPING, FIAT));
        rides1.add(new Ride("ten",HOME, CASTLE, DATE, 4, 20, new BigDecimal(25), SHOPPING, OPEL));
        rides1.add(new Ride("ELEVEN",CASTLE, CASTLE, DATE, 5, 30, new BigDecimal(45), SHOPPING, OPEL));
    }

    @Test
    void noFilterTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools = new FilterTools(table, 0, 0, 0, 0, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));
    }

    @Test
    void searchTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", "", "A");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(6),
                rides1.get(7),
                rides1.get(8)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", "", "AAA");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(7),
                rides1.get(8)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", "", "e");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(2),
                rides1.get(4),
                rides1.get(6),
                rides1.get(8),
                rides1.get(9)
        );
    }

    @Test
    void passengerTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, -1, 0, 0, 0, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));

        tools = new FilterTools(table, 0, -5, 0, 0, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));

        tools = new FilterTools(table, 0, 2, 0, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(1),
                rides1.get(6),
                rides1.get(7)
        );

        tools = new FilterTools(table, 4, 0, 0, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(3),
                rides1.get(4),
                rides1.get(5),
                rides1.get(9),
                rides1.get(10)
        );

        tools = new FilterTools(table, -5, 2, 0, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(1),
                rides1.get(6),
                rides1.get(7)
        );

        tools = new FilterTools(table, 5, -3, 0, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(4),
                rides1.get(5),
                rides1.get(10)
        );

        tools = new FilterTools(table, 2, 4, 0, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(1),
                rides1.get(2),
                rides1.get(3),
                rides1.get(7),
                rides1.get(8),
                rides1.get(9)
        );
    }

    @Test
    void distanceTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, 0, 0, -5, 0, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));

        tools = new FilterTools(table, 0, 0, 0, -5, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));

        tools = new FilterTools(table, 0, 0, 0, 20, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(1),
                rides1.get(2),
                rides1.get(3),
                rides1.get(6),
                rides1.get(7),
                rides1.get(8),
                rides1.get(9)
        );

        tools = new FilterTools(table, 0, 0, 20, 0, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(3),
                rides1.get(4),
                rides1.get(5),
                rides1.get(9),
                rides1.get(10)
        );

        tools = new FilterTools(table, 0, 0, 5.49, 6, "", "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(6)
        );
    }

    @Test
    void destinationTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, 0, 0, 0, 0, HOME.toString(), "", "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(1),
                rides1.get(2),
                rides1.get(3),
                rides1.get(6),
                rides1.get(7),
                rides1.get(8),
                rides1.get(9)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", HOME.toString(), "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(5)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, SHOP.toString(), HOME.toString(), "", "");
        tools.filterTable();
        assertEquals(0, table.getRowCount());

        tools = new FilterTools(table, 0, 0, 0, 0, HOME.toString(), CASTLE.toString(), "", "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(2),
                rides1.get(3),
                rides1.get(8),
                rides1.get(9)
        );
    }

    @Test
    void carTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", FIAT.toString(), "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(0),
                rides1.get(2),
                rides1.get(6),
                rides1.get(8)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", VOLVO.toString(), "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(1),
                rides1.get(3),
                rides1.get(4),
                rides1.get(5),
                rides1.get(7),
                rides1.get(9),
                rides1.get(10)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", BMW.toString(), "");
        tools.filterTable();
        assertEquals(0, table.getRowCount());
    }

    @Test
    void complexTest() {
        JTable table = new JTable();
        table.setModel(new RecordTableModel(rides1));
        FilterTools tools;

        tools = new FilterTools(table, 1, 3, -20, -6, HOME.toString(), "", "", "seven");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(6)
        );

        tools = new FilterTools(table, 2, 0, 1.999, 15, HOME.toString(), SHOP.toString(), VOLVO.toString(), "");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(1),
                rides1.get(7)
        );

        tools = new FilterTools(table, 0, 0, 15.11, 0, "", CASTLE.toString(), "", "e");
        tools.filterTable();
        assertThat(getRides(table)).containsExactly(
                rides1.get(4),
                rides1.get(9)
        );

        tools = new FilterTools(table, 0, 0, 0, 0, "", "", "", "");
        tools.filterTable();
        containsAll(getRides(table));
    }

    private void containsAll(List<Ride> list) {
        assertThat(list).containsExactly(
                rides1.get(0),
                rides1.get(1),
                rides1.get(2),
                rides1.get(3),
                rides1.get(4),
                rides1.get(5),
                rides1.get(6),
                rides1.get(7),
                rides1.get(8),
                rides1.get(9),
                rides1.get(10)
        );
    }

    private List<Ride> getRides(JTable table) {
        List<Ride> rides = new ArrayList<>();
        String name = null;
        Destination from = null;
        Destination to = null;
        LocalDate date = null;
        int passengers = 0;
        double distance = 0;
        BigDecimal price = null;
        List<Category> category = null;
        Auto car = null;
        for (int row = 0; row < table.getRowCount(); row++) {
            for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                Column column = Column.values()[columnIndex];
                switch (column) {
                    case NAME -> name = (String) table.getValueAt(row, columnIndex);
                    case FROM -> from = (Destination) table.getValueAt(row, columnIndex);
                    case TO -> to = (Destination) table.getValueAt(row, columnIndex);
                    case DATE -> date = (LocalDate) table.getValueAt(row, columnIndex);
                    case PERSONS -> passengers = (int) table.getValueAt(row, columnIndex);
                    case DISTANCE -> distance = (double) table.getValueAt(row, columnIndex);
                    case PRICE -> price = (BigDecimal) table.getValueAt(row, columnIndex);
                    case CATEGORIES -> category = (List<Category>) table.getValueAt(row, columnIndex);
                    case AUTO -> car = (Auto) table.getValueAt(row, columnIndex);
                }
            }
            rides.add(new Ride(name, from, to, date, passengers, distance, price, category, car));
        }
        return rides;
    }
}
