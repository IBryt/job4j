package ru.job4j.servlets;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Countries;
import ru.job4j.model.Towns;
import ru.job4j.model.UserJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class AjaxController extends HttpServlet {
    private Map<String, UserJson> users = new ConcurrentHashMap<>();
    private static final Validate LOGIC = ValidateService.getInstance();
    private Map<Integer, Countries> countries = LOGIC.getCountries();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text,/json");
        String type = req.getParameter("type");
        String json = "";
        if (type.equals("countries")) {
            json = new ObjectMapper().writeValueAsString(countries);
        } else if (type.equals("users")) {
            json = new ObjectMapper().writeValueAsString(users);
        } else if (type.equals("towns")) {
            String country = req.getParameter("country");
            Map<Integer, Towns> towns = LOGIC.getTowns(country);
            json = new ObjectMapper().writeValueAsString(towns);
        }
        PrintWriter writer = resp.getWriter();
        writer.append(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String lastname = req.getParameter("lastname");
        Countries country = LOGIC.getCountry(req.getParameter("country"));
        Towns town = LOGIC.getTown(req.getParameter("town"), country);
        if (country != null && town != null) {
            users.put(name, new UserJson(name, lastname, email, country, town));
        }
    }
}
