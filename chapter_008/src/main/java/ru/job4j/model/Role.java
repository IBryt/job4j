package ru.job4j.model;

public class Role {
    private int id;
    private String name;
    private boolean editAll;

    public Role() {

    }

    public Role(int id, String name, boolean editAll) {
        this.id = id;
        this.name = name;
        this.editAll = editAll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEditAll() {
        return editAll;
    }

    public void setEditAll(boolean editAll) {
        this.editAll = editAll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
