package ru.job4j.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class UserUpdateController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserUpdateController.class.getName());
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            User user = logic.findById(Integer.parseInt(id));
            if (user != null) {
                req.setAttribute("user", user);
                if (req.getSession().getAttribute("login") != null) {
                    if ((boolean) req.getSession().getAttribute("editAll")
                            || user.getLogin().equals(req.getSession().getAttribute("login"))) {
                        req.setAttribute("roles", logic.getRoles());
                        req.getRequestDispatcher("/WEB-INF/views/UserUpdateServlet.jsp").forward(req, resp);
                    } else {
                        //                    req.setAttribute("error", "Not editing right");
                        //                    req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
                        resp.sendRedirect(String.format("%s/", req.getContextPath()));
                    }
                }
            }
        } else {
            req.setAttribute("roles", logic.getRoles());
            req.setAttribute("user", new User("", "", "", new Timestamp(new Date().getTime()), new Role()));
            req.getRequestDispatcher("/WEB-INF/views/UserUpdateServlet.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (!id.equals("0")) {
            User user = logic.findById(Integer.parseInt(id));
            user.setName(req.getParameter("name"));
            user.setLogin(req.getParameter("login"));
            user.setEmail(req.getParameter("email"));
            Role role = logic.getRoleById(Integer.parseInt(req.getParameter("role")));
            user.setRole(role);
            logic.update(user);
        } else {
            logic.add(new User(
                            req.getParameter("name"),
                            req.getParameter("login"),
                            req.getParameter("email"),
                            new Timestamp(new Date().getTime()),
                            logic.getRoleById(Integer.parseInt(req.getParameter("role")))
                    )
            );
        }

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
