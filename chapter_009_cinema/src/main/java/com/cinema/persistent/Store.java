package com.cinema.persistent;

import com.cinema.model.Hall;
import com.cinema.model.Tickets;

import java.util.List;

public interface Store<T>  {
    List<Hall> getPlaceHall();

    Tickets addTicket(int idHall, String name, String phone);
}
