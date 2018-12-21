package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;


public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServlet.class);
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        logic.findAll().entrySet().stream().map(entry -> entry.getValue().toString()).forEach(s ->
                writer.append(s).append("<br>")
        );
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        operations(req);
        doGet(req, resp);
    }

    private void operations(HttpServletRequest req) {
        if ("add".equals(req.getParameter("action"))) {
            String name = req.getParameter("name");
            User user = new User(name, "login", "email", new Timestamp(new Date().getTime()));
            logic.add(user);
        }
        if ("update".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            logic.update(id, name);
        }
        if ("delete".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            logic.delete(id);
        }
    }
}

