package cz.muni.fi.pv168.seminar01.delta.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Class representing drive record.
 *
 * @author Jiri Zak
 */
public class Ride {
    private final Long id;
    private final String name;
    private final Destination from;
    private final Destination to;
    private final LocalDate date;
    private final int passengerCount;
    private final double distance;
    private final BigDecimal price;
    private final List<Category> categories;
    private final Auto auto;

    public Ride(Long id, String name, Destination from, Destination to, LocalDate date, int passengerCount,
                double distance, BigDecimal price, List<Category> categories, Auto auto) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.date = date;
        this.passengerCount = passengerCount;
        this.distance = distance;
        this.price = price;
        this.categories = categories;
        this.auto = auto;
    }

    public Ride(String name, Destination from, Destination to, LocalDate date, int passengerCount,
                double distance, BigDecimal price, List<Category> categories, Auto auto) {
        this(null, name, from, to, date, passengerCount, distance, price, categories, auto);
    }

    public String export() {
        var categoryNames = categories.stream().map(Category::getName).toList();
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", name, from, to, date, passengerCount, distance, price,
                String.join(";", categoryNames));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Destination getFrom() {
        return from;
    }

    public Destination getTo() {
        return to;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double getDistance() {
        return distance;
    }

    public BigDecimal getPrice() { return price;}

    public Double getPriceInDouble() { return price.doubleValue();}

    public List<Category> getCategories() {
        return categories;
    }

    public Auto getAuto() {
        return auto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return passengerCount == ride.passengerCount
                && Double.compare(ride.distance, distance) == 0
                && Objects.equals(id, ride.id)
                && Objects.equals(name, ride.name)
                && Objects.equals(from, ride.from)
                && Objects.equals(to, ride.to)
                && Objects.equals(date, ride.date)
                && Objects.equals(price, ride.price)
                && Objects.equals(categories, ride.categories)
                && Objects.equals(auto, ride.auto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, from, to, date, passengerCount, distance, price, categories, auto);
    }
}
