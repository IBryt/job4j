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
import java.sql.Timestamp;
import java.util.Date;

public class UserCreateController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserCreateController.class.getName());
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/UserCreateServlet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        operations(req);
        resp.sendRedirect(req.getContextPath());
    }


    private void operations(HttpServletRequest req) {
        if ("add".equals(req.getParameter("action"))) {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            User user = new User(name, login, email, new Timestamp(new Date().getTime()));
            logic.add(user);
        }
    }
}
