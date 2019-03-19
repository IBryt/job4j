package ru.job4j.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.impl.cars.Car;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarService {
    private static final Logger LOG = LogManager.getLogger(CarService.class.getName());
    private final static ImplDao DAO = ImplDao.getInstance();
    private final static CarService INSTANCE = new CarService();

    private CarService() {
        super();
    }

    public static CarService getInstance() {
        return INSTANCE;
    }

    public void add(Car car) {
        DAO.add(car);
    }

    public List<Car> getAll() {
        return DAO.getAll(Car.class);
    }
}
