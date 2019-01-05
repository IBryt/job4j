package ru.job4j.model;

public class UserJson {
    private String name;
    private String lastname;
    private String email;
    private Countries country;
    private Towns town;

    public UserJson(String name, String lastname, String email, Countries country, Towns town) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.country = country;
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public Towns getTown() {
        return town;
    }

    public void setTown(Towns town) {
        this.town = town;
    }
}
