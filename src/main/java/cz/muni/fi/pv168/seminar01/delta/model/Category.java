package cz.muni.fi.pv168.seminar01.delta.model;

import java.util.Objects;

/**
 * Class representing Category
 * @author Veronika Lenkova
 */
public class Category {

    private Long id;
    private String name;
    private Long parentId;

    public Category(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Category(String name) {
        this(null, name, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() { return parentId; }

    public void setParent(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(parentId, category.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId);
    }
}
