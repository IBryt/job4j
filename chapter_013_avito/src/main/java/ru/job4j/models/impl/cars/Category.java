package ru.job4j.models.impl.cars;

import ru.job4j.models.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Category extends Model {

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Car> cars = new HashSet<>();

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Category category = (Category) o;

        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
