package com.cinema.servlets;

import com.cinema.logic.Validate;
import com.cinema.logic.ValidateService;
import com.cinema.model.Hall;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class HallServlet extends HttpServlet {
    private static final Validate LOGIC = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Hall> hall = LOGIC.getPlaceHall();
        resp.setContentType("text,/json");
        Set<Integer> set = hall.stream().map(Hall::getRow).collect(Collectors.toSet());
        HashMap<Integer, List> hashMap = new HashMap<>();
        for (int i: set) {
            hashMap.put(i, hall.stream().filter(v-> v.getRow() == i).collect(Collectors.toList()));
        }
        String json = new ObjectMapper().writeValueAsString(hashMap);
        PrintWriter writer = resp.getWriter();
        writer.append(json);
        writer.flush();
    }
}
