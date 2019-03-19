package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.impl.cars.Car;
import ru.job4j.service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class ControllerMainPage extends HttpServlet {
    private final static CarService SERVICE = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        final List<Car> all = SERVICE.getAll();
//        resp.setContentType("text/json");
//        String json = new ObjectMapper().writeValueAsString(all);
//        try (final OutputStreamWriter streamWriter = new OutputStreamWriter(resp.getOutputStream(), "utf-8");
//             final PrintWriter writer = new PrintWriter(streamWriter)) {
//            writer.append(json);
//            writer.flush();
//        }
        resp.sendRedirect(String.format("%s/content.html", req.getContextPath()));
    }
}
