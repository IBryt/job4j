package ru.job4j.model;

public class Towns {
    private int id;
    private String name;
    private Countries countries;

    public Towns() {
    }

    public Towns(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Towns(int id, String name, Countries countries) {
        this.id = id;
        this.name = name;
        this.countries = countries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Countries getCountries() {
        return countries;
    }

    public void setCountries(Countries countries) {
        this.countries = countries;
    }
}
