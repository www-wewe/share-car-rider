package cz.muni.fi.pv168.seminar01.delta.data.storage.mapper;

import cz.muni.fi.pv168.seminar01.delta.data.storage.entity.CategoryEntity;
import cz.muni.fi.pv168.seminar01.delta.model.Category;


/**
 * Class for converting CategoryEntity <-> Category
 *
 * @author Martin Vrzon
 */
public class CategoryMapper implements EntityModelMapper<CategoryEntity, Category> {

    @Override
    public CategoryEntity mapToEntity(Category model) {
        if (model == null)
            return null;

        return new CategoryEntity(model.getId(), model.getName(), model.getParentId());
    }

    @Override
    public Category mapToModel(CategoryEntity entity) {
        if (entity == null)
            return null;

        return new Category(entity.id(), entity.name(), entity.parentId());
    }
}
