package cz.muni.fi.pv168.seminar01.delta.data.processing;

import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrej Gafrik
 * Utilities calculating statistics
 */

public class StatisticsTools {
    private final List<Ride> rides;

    public StatisticsTools(List<Ride> rides) {
        this.rides = rides;
    }

    public int getRideCount() {
        return rides.size();
    }

    public double totalDistance() {
        return Math.max(0, rides.stream().mapToDouble(ride -> Math.max(0, ride.getDistance())).sum());
    }

    public BigDecimal totalPrice() {
        return rides.stream().map(Ride::getPrice).reduce(BigDecimal.ZERO, (subtotal, element) -> subtotal.add(BigDecimal.ZERO.max(element)));
    }

    public double maxDistance() {
        return Math.max(0, rides.stream().mapToDouble(Ride::getDistance).max().orElse(0));
    }

    public BigDecimal maxPrice() {
        return rides.stream().map(Ride::getPrice).reduce(BigDecimal.ZERO, BigDecimal::max);
    }
}
