package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.RideRepository;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekCalendar;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekChange;
import cz.muni.fi.pv168.seminar01.delta.gui.language.StatisticsLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Chart extends JDialog {
    private static final int NUMBER_OF_WEEKS = 5;
    private final Map<String, List<BigDecimal>> priceKm;
    private final RideRepository rides;
    private final AutoRepository cars;
    private final WeekCalendar weekCalendar;
    private final ResourceBundle text;

    public Chart(Repository<Ride> rides, Repository<Auto> cars, Locale locale) {
        this.text = ResourceBundle.getBundle(StatisticsLanguage.PATH, locale);
        this.priceKm = new HashMap<>();
        this.rides = (RideRepository) rides;
        this.cars = (AutoRepository) cars;
        this.weekCalendar = new WeekCalendar();
        computeData();

        this.setTitle(text.getString("chartTitle"));
        setContentPane(build());
        setModal(true);
    }

    private void computeData() {
        rollBackCalendar(NUMBER_OF_WEEKS - 1);
        initMap();
        for (int i = 0; i < NUMBER_OF_WEEKS; i++) {
            var currentDate = weekCalendar.getCurrentlySetDate();
            var currentWeekStartDate = WeekCalendar.getWeekStartDate(currentDate);
            var currentWeekEndDate = WeekCalendar.getWeekEndDate(currentDate);
            Collection<Ride> ridesInWeek = rides.fetchAllInRange(currentWeekStartDate, currentWeekEndDate);

            double km;
            BigDecimal price;

            for (String licencePlate : priceKm.keySet()) {
                km = ridesInWeek.stream()
                        .filter(ride -> Objects.equals(ride.getAuto().getLicensePlate(), licencePlate))
                        .map(Ride::getDistance)
                        .reduce(0D, Double::sum);
                price = ridesInWeek.stream()
                        .filter(ride -> Objects.equals(ride.getAuto().getLicensePlate(), licencePlate))
                        .map(Ride::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (km != 0) {
                    priceKm.get(licencePlate).add(price.divide(BigDecimal.valueOf(km), RoundingMode.HALF_UP));
                } else {
                    priceKm.get(licencePlate).add(BigDecimal.ZERO);
                }
            }
            weekCalendar.shiftWeek(WeekChange.NEXT);
        }
    }

    private void rollBackCalendar(int weeks) {
        for (int i = 0; i < weeks; i++) {
            weekCalendar.shiftWeek(WeekChange.PREV);
        }
    }

    private void initMap() {
        List<String> licencePlates = new ArrayList<>( cars.findAll().stream().map(Auto::getLicensePlate).distinct().toList());
        if (licencePlates.size() == 0) {
            licencePlates.add(text.getString("noCars"));
        }
        for (String licencePlate : licencePlates) {
            priceKm.put(licencePlate, new ArrayList<>());
        }
    }

    private XChartPanel<CategoryChart> build() {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(text.getString("chartTitle"))
                .xAxisTitle(text.getString("week"))
                .yAxisTitle("KČ/km")
                .build();
        List<String> weekLabels = new ArrayList<>();
        rollBackCalendar(NUMBER_OF_WEEKS);
        for (int i = 0; i < NUMBER_OF_WEEKS; i++) {
            var currentDate = weekCalendar.getCurrentlySetDate();
            var currentWeekStartDate = WeekCalendar.getWeekStartDate(currentDate);
            weekLabels.add(WeekCalendar.formatDate(currentWeekStartDate));
            weekCalendar.shiftWeek(WeekChange.NEXT);
        }

        for (String licencePlate : priceKm.keySet()) {
            chart.addSeries(licencePlate, weekLabels, priceKm.get(licencePlate).stream().map(BigDecimal::doubleValue).toList());
        }
        return new XChartPanel<>(chart) ;
    }
}
