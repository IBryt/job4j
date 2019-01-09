package com.cinema.model;

public class Hall {
    private int id;
    private String name;
    private int row;
    private int place;
    private boolean occupied;

    public Hall(int id, String name, int row, int place, boolean occupied) {
        this.id = id;
        this.name = name;
        this.row = row;
        this.place = place;
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return String.format(
                "Hall{id= %d, name= %s , row= %d , place= %d, occupied= %s}",
                id, name, row, place, occupied
        );
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
