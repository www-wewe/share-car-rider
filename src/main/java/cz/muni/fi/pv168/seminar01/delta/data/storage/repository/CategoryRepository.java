package cz.muni.fi.pv168.seminar01.delta.data.storage.repository;

import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.CategoryDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.CategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.EntityModelMapper;
import cz.muni.fi.pv168.seminar01.delta.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a storage for the Category objects
 *
 * @author Martin Vrzon
 */
public class CategoryRepository implements Repository<Category> {

    private final CategoryDao dao;
    private final EntityModelMapper<CategoryEntity, Category> mapper;
    private List<Category> categories = new ArrayList<>();

    public CategoryRepository(
            CategoryDao dao,
            EntityModelMapper<CategoryEntity, Category> mapper
    ) {
        this.dao = dao;
        this.mapper = mapper;
        this.refresh();
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Optional<Category> findByPK(Object pk) {
        return categories.stream()
                .filter(d -> d.getId() == pk)
                .findFirst();
    }

    @Override
    public Optional<Category> findByIndex(int index) {
        if (0 <= index && index < getSize())
            return Optional.of(categories.get(index));
        return Optional.empty();
    }

    public Category findByName(String name) {
        for(Category category : categories){
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        refresh();
        return categories;
    }

    @Override
    public void refresh() {
        categories = dao.findAll().stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void create(Category category) {
        dao.create(mapper.mapToEntity(category));
        refresh();
    }

    @Override
    public void update(Category category) {
        dao.update(mapper.mapToEntity(category));
        refresh();
    }

    @Override
    public void deleteByIndex(int index) {
        Category Category = categories.get(index);
        dao.deleteByPK(Category.getId());
        categories.remove(index);
        refresh();
    }

    public void deleteById(Long id){
        for (int i = 0; i < categories.size(); i++){
            if (categories.get(i).getId().equals(id)){
                deleteByIndex(i);
                break;
            }
        }
        refresh();
    }
}
