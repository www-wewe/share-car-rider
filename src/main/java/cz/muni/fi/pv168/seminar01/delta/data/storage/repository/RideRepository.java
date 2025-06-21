package cz.muni.fi.pv168.seminar01.delta.data.storage.repository;

import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.RideDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.RideEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.EntityModelMapper;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekCalendar;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents a storage for the Ride objects
 *
 * @author Martin Vrzon
 */
public class RideRepository implements Repository<Ride> {

    private final RideDao dao;
    private final EntityModelMapper<RideEntity, Ride> mapper;
    private List<Ride> rides = new ArrayList<>();

    public RideRepository(
            RideDao dao,
            EntityModelMapper<RideEntity, Ride> mapper
    ) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public int getSize() {
        return rides.size();
    }

    @Override
    public Optional<Ride> findByPK(Object pk) {
        return rides.stream()
                .filter(d -> d.getId() == pk)
                .findFirst();
    }

    @Override
    public Optional<Ride> findByIndex(int index) {
        if (0 <= index && index < getSize())
            return Optional.of(rides.get(index));
        return Optional.empty();
    }

    @Override
    public List<Ride> findAll() {
        var rideEntities =  dao.findAll();
        rides = new ArrayList<Ride>();
        for (RideEntity re : rideEntities) {
            rides.add(mapper.mapToModel(re));
        }
        return Collections.unmodifiableList(rides);
    }

    @Override
    public void refresh() {
        // IMPORTANT refreshes with TODAY DATE, not with the set one ---> for that use fetchAllInRange() method
        var currentDate = WeekCalendar.getTodayDate();
        var currentWeekStartDate = WeekCalendar.getWeekStartDate(currentDate);
        var currentWeekEndDate = WeekCalendar.getWeekEndDate(currentDate);
        fetchAllInRange(currentWeekStartDate, currentWeekEndDate);
    }

    @Override
    public void create(Ride ride) {
        dao.create(mapper.mapToEntity(ride));
    }

    @Override
    public void update(Ride ride) {
        dao.update(mapper.mapToEntity(ride));
    }

    @Override
    public void deleteByIndex(int index) {
        Ride Ride = rides.get(index);
        dao.deleteByPK(Ride.getId());
        rides.remove(index);
    }

    public Collection<Ride> fetchAllInRange(LocalDate startWeekDate, LocalDate endWeekDate) {
        var rideEntities =  dao.findAllInRange(Date.valueOf(startWeekDate), Date.valueOf(endWeekDate));
        rides = new ArrayList<Ride>();
        for (RideEntity re : rideEntities) {
            rides.add(mapper.mapToModel(re));
        }
        return Collections.unmodifiableList(rides);
    }
}
