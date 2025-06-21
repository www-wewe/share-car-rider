package cz.muni.fi.pv168.seminar01.delta.data.storage.repository;

import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.AutoDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.AutoEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.EntityModelMapper;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a storage for the Auto objects
 *
 * @author Martin Vrzon
 */
public class AutoRepository implements Repository<Auto> {

    private final AutoDao dao;
    private final EntityModelMapper<AutoEntity, Auto> mapper;
    private List<Auto> autos = new ArrayList<>();

    public AutoRepository(
            AutoDao dao,
            EntityModelMapper<AutoEntity, Auto> mapper
    ) {
        this.dao = dao;
        this.mapper = mapper;
        this.refresh();
    }

    @Override
    public int getSize() {
        return autos.size();
    }

    @Override
    public Optional<Auto> findByPK(Object pk) {
        return autos.stream()
                .filter(auto -> auto.getId() == pk)
                .findFirst();
    }

    @Override
    public Optional<Auto> findByIndex(int index) {
        if (0 <= index && index < getSize())
            return Optional.of(autos.get(index));
        return Optional.empty();
    }

    @Override
    public List<Auto> findAll() {
        refresh();
        return autos;
    }

    @Override
    public void refresh() {
        autos = dao.findAll().stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void create(Auto auto) {
        dao.create(mapper.mapToEntity(auto));
        refresh();
    }

    @Override
    public void update(Auto auto) {
        dao.update(mapper.mapToEntity(auto));
        refresh();
    }

    @Override
    public void deleteByIndex(int index) {
        Auto auto = autos.get(index);
        dao.deleteByPK(auto.getId());
        autos.remove(index);
        refresh();
    }

    public void deleteById(Long id){
        for (int i = 0; i < autos.size(); i++){
            if (autos.get(i).getId().equals(id)){
                deleteByIndex(i);
                break;
            }
        }
        refresh();
    }

    private List<Auto> fetchAll() {
        return dao.findAll().stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
