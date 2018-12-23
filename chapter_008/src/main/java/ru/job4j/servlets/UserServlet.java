package ru.job4j.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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


public class UserServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(EchoServlet.class.getName());
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        operations(req);
        //doGet(req, resp);
        resp.sendRedirect(req.getContextPath());
    }

    private void operations(HttpServletRequest req) {
        if ("add".equals(req.getParameter("action"))) {
            String name = req.getParameter("name");
            User user = new User(name, "login", "email", new Timestamp(new Date().getTime()));
            logic.add(user);
        }
        if ("update".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = logic.findById(id);
            user.setName(req.getParameter("name"));
            logic.update(user);
        }
        if ("delete".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            logic.delete(id);
        }
    }
}

