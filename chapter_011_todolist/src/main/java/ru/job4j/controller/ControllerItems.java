package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.*;
import ru.job4j.logic.ValidateItem;
import ru.job4j.models.impl.Item;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class ControllerItems extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(ControllerItems.class.getName());
    private static final ValidateItem VALIDATE = ValidateItem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        boolean done = Boolean.valueOf(req.getParameter("done"));
        final List<Item> items = VALIDATE.getAll(done);
        String json = new ObjectMapper().writeValueAsString(items);
        try (final OutputStreamWriter streamWriter = new OutputStreamWriter(resp.getOutputStream(), "utf-8");
             final PrintWriter writer = new PrintWriter(streamWriter)) {
            writer.append(json);
            writer.flush();
        }
    }
}
