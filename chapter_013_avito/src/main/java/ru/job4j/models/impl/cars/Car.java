package ru.job4j.models.impl.cars;

import ru.job4j.CustomNamingStrategy;
import ru.job4j.models.Model;
import ru.job4j.models.impl.users.Users;

import javax.persistence.*;
import java.util.*;

//@Entity
public class Car extends Model {

    private String name;

   // @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Category category;

  //  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Brand brand;

  //  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Carcase carcase;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = CustomNamingStrategy.PREFIX + "car_authors",
//            joinColumns = @JoinColumn(name="car_id"),
//            inverseJoinColumns = @JoinColumn(name="authors_id")
//    )
    private Set<Users> authors = new HashSet<>();

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Carcase getCarcase() {
        return carcase;
    }

    public void setCarcase(Carcase carcase) {
        this.carcase = carcase;
    }

    public Set<Users> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Users> authors) {
        this.authors = authors;
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

        Car car = (Car) o;

        if (name != null ? !name.equals(car.name) : car.name != null) {
            return false;
        }
        if (saleStatus != car.saleStatus) {
            return false;
        }
        if (category != null ? !category.equals(car.category) : car.category != null) {
            return false;
        }
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) {
            return false;
        }
        return carcase != null ? carcase.equals(car.carcase) : car.carcase == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (saleStatus != null ? saleStatus.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (carcase != null ? carcase.hashCode() : 0);
        return result;
    }
}
