package cz.muni.fi.pv168.seminar01.delta.data.storage.repository;

import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.DestinationDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.DestinationEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.EntityModelMapper;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a storage for the Destination objects
 *
 * @author Martin Vrzon
 */
public class DestinationRepository implements Repository<Destination> {

    private final DestinationDao dao;
    private final EntityModelMapper<DestinationEntity, Destination> mapper;
    private List<Destination> destinations = new ArrayList<>();

    public DestinationRepository(
            DestinationDao dao,
            EntityModelMapper<DestinationEntity, Destination> mapper
    ) {
        this.dao = dao;
        this.mapper = mapper;
        this.refresh();
    }

    @Override
    public int getSize() {
        return destinations.size();
    }

    @Override
    public Optional<Destination> findByPK(Object pk) {
        return destinations.stream()
                .filter(d -> d.getId() == pk)
                .findFirst();
    }

    @Override
    public Optional<Destination> findByIndex(int index) {
        if (0 <= index && index < getSize())
            return Optional.of(destinations.get(index));
        return Optional.empty();
    }

    public Destination findByName(String name) {
        for(Destination destination : destinations){
            if (destination.getName().equals(name))
                return destination;
        }
        return null;
    }

    @Override
    public List<Destination> findAll() {
        refresh();
        return destinations;
    }

    @Override
    public void refresh() {
        destinations = dao.findAll().stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void create(Destination destination) {
        dao.create(mapper.mapToEntity(destination));
        refresh();
    }

    @Override
    public void update(Destination destination) {
        dao.update(mapper.mapToEntity(destination));
        refresh();
    }

    @Override
    public void deleteByIndex(int index) {
        Destination Destination = destinations.get(index);
        dao.deleteByPK(Destination.getId());
        destinations.remove(index);
        refresh();
    }

    public void deleteById(Long id){
        for (int i = 0; i < destinations.size(); i++){
            if (destinations.get(i).getId().equals(id)){
                deleteByIndex(i);
                break;
            }
        }
        refresh();
    }

    private List<Destination> fetchAll() {
        return dao.findAll().stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

