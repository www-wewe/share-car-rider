package cz.muni.fi.pv168.seminar01.delta.model;

import java.util.Objects;

/**
 * Class representing Destination
 * @author Veronika Lenkova
 */
public class Destination {
    private Long id;
    private String name;

    public Destination(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Destination(String name) {
        this(null, name);
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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return Objects.equals(id, that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
