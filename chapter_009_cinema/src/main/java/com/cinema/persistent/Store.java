package com.cinema.persistent;

import com.cinema.model.Hall;
import java.util.List;

public interface Store<T>  {
    List<Hall> getPlaceHall();

}
