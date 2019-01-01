package ru.job4j.servlets;

import java.io.PrintWriter;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.UserJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class AjaxController extends HttpServlet {
    private Map<String, UserJson> users = new ConcurrentHashMap();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text,/json");
        String json = new ObjectMapper().writeValueAsString(users);
        PrintWriter writer = resp.getWriter();
        writer.append(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String lastname = req.getParameter("lastname");
        users.put(name, new UserJson(name, lastname, email));
    }
}
