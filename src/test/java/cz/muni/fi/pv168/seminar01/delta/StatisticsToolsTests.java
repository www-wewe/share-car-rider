package cz.muni.fi.pv168.seminar01.delta;

import cz.muni.fi.pv168.seminar01.delta.model.*;
import cz.muni.fi.pv168.seminar01.delta.data.processing.StatisticsTools;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsToolsTests {
    private final List<Ride> okRides;
    private final List<Ride> negativeRides;
    private final List<Ride> someNegativeRides;

    private final Destination home = new Destination("Home");
    private final Destination shop = new Destination("Shop");
    private final List<Category> shopping = List.of(new Category("Nakupovanie"));
    private final Auto car = new Auto("1B2-1234", "Trabant", FuelType.PETROL);

    StatisticsToolsTests() {
        this.okRides = new ArrayList<>();
        this.negativeRides = new ArrayList<>();
        this.someNegativeRides = new ArrayList<>();
        fillOkRides();
        fillNegativeRides();
        fillSomeNegativeRides();
    }

    private void fillOkRides() {
        okRides.add(new Ride("One",home, shop, LocalDate.of(2022, 11, 2), 1, 5.49, new BigDecimal(15), shopping, car));
        okRides.add(new Ride("Two",home, shop, LocalDate.of(2022, 11, 2), 2, 10, new BigDecimal(30), shopping, car));
        okRides.add(new Ride("Three",home, shop, LocalDate.of(2022, 11, 2), 3, 15, new BigDecimal(20), shopping, car));
        okRides.add(new Ride("Four",home, shop, LocalDate.of(2022, 11, 2), 4, 20, new BigDecimal(25), shopping, car));
        okRides.add(new Ride("Five",home, shop, LocalDate.of(2022, 11, 2), 5, 30, new BigDecimal(45), shopping, car));
        okRides.add(new Ride("Six",home, shop, LocalDate.of(2022, 11, 2), 6, 30, new BigDecimal(45), shopping, car));
    }

    private void fillNegativeRides() {
        negativeRides.add(new Ride("One",home,shop,LocalDate.of(2022,11,2),-1,-5.49,new BigDecimal("-15"),shopping,car));
        negativeRides.add(new Ride("Two",home, shop, LocalDate.of(2022, 11, 2), -2, -10, new BigDecimal("-10"),shopping,car));
        negativeRides.add(new Ride("Three",home, shop, LocalDate.of(2022, 11, 2), -3, -15, new BigDecimal("-20"),shopping,car));
        negativeRides.add(new Ride("Four",home, shop, LocalDate.of(2022, 11, 2), -4, -20, new BigDecimal("-25"),shopping,car));
        negativeRides.add(new Ride("Five",home, shop, LocalDate.of(2022, 11, 2), -5, -30, new BigDecimal("-45"),shopping,car));
        negativeRides.add(new Ride("Six",home, shop, LocalDate.of(2022, 11, 2), -6, -30, new BigDecimal("-45"),shopping,car));
    }

    private void fillSomeNegativeRides() {
        someNegativeRides.add(new Ride("One",home, shop, LocalDate.of(2022, 11, 2), -1, -5.49, new BigDecimal(15), shopping, car));
        someNegativeRides.add(new Ride("Two",home, shop, LocalDate.of(2022, 11, 2), 2, -10, new BigDecimal(-30), shopping, car));
        someNegativeRides.add(new Ride("Three",home, shop, LocalDate.of(2022, 11, 2), 3, 15.0, new BigDecimal(-20), shopping, car));
        someNegativeRides.add(new Ride("Four",home, shop, LocalDate.of(2022, 11, 2), -4, -20, new BigDecimal(25), shopping, car));
        someNegativeRides.add(new Ride("Five",home, shop, LocalDate.of(2022, 11, 2), -5, 30, new BigDecimal(-45), shopping, car));
        someNegativeRides.add(new Ride("Six",home, shop, LocalDate.of(2022, 11, 2), -6, -30, new BigDecimal(-45), shopping, car));
    }

    @Test
    void emptyRidesTest() {
        StatisticsTools tools = new StatisticsTools(new ArrayList<>());
        assertEquals(0, tools.getRideCount());
        assertEquals(0, tools.totalDistance());
        assertEquals(BigDecimal.ZERO, tools.totalPrice());
        assertEquals(0, tools.maxDistance());
        assertEquals(BigDecimal.ZERO, tools.maxPrice());
    }

    @Test
    void okRidesTest() {
        StatisticsTools tools = new StatisticsTools(okRides);
        assertEquals(110.49, tools.totalDistance(), 0.000001);
        assertEquals(new BigDecimal(180), tools.totalPrice());
        assertEquals(30, tools.maxDistance(), 0.000001);
        assertEquals(new BigDecimal(45), tools.maxPrice());
    }

    @Test
    void negativeRidesTest() {
        StatisticsTools tools = new StatisticsTools(negativeRides);
        assertEquals(0, tools.totalDistance());
        assertEquals(BigDecimal.ZERO, tools.totalPrice());
        assertEquals(0, tools.maxDistance());
        assertEquals(BigDecimal.ZERO, tools.maxPrice());
    }

    @Test
    void someNegativeRidesTest() {
        StatisticsTools tools = new StatisticsTools(someNegativeRides);
        assertEquals(45, tools.totalDistance(), 0.000001);
        assertEquals(new BigDecimal(40), tools.totalPrice());
        assertEquals(30, tools.maxDistance(), 0.000001);
        assertEquals(new BigDecimal(25), tools.maxPrice());
    }
}
