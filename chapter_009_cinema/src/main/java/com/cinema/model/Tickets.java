package com.cinema.model;

public class Tickets {
    private int id;
    private Hall hall;
    private Account account;

    public Tickets(int id, Hall hall, Account account) {
        this.id = id;
        this.hall = hall;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
