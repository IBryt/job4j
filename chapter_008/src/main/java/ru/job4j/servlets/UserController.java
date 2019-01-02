package ru.job4j.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;


public class UserController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((boolean) req.getSession().getAttribute("editAll")) {
            req.setAttribute("users", logic.findAll());
        } else {
            User user = logic.findById((int) req.getSession().getAttribute("id"));
            HashMap<Integer, User> users = new HashMap<>();
            users.put(user.getId(), user);
            req.setAttribute("users", users);
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        operations(req);
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private void operations(HttpServletRequest req) {
        if ("delete".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            logic.delete(id);
        }
    }
}

