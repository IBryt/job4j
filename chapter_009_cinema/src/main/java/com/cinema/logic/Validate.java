package com.cinema.logic;
import com.cinema.model.Hall;
import com.cinema.model.Tickets;

import java.util.List;

public interface Validate {
    List<Hall> getPlaceHall();

    Tickets addTicket(int idHall, String name, String phone);
}
