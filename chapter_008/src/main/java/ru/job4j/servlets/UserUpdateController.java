package ru.job4j.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.logic.Validate;
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
import java.util.List;
import java.util.stream.Collectors;

public class UserUpdateController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserUpdateController.class.getName());
    private static final Validate LOGIC = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean editAll = (boolean) req.getSession().getAttribute("editAll");
        req.setAttribute("roles", getRoles(editAll));
        if (id != null) {
            User user = LOGIC.findById(Integer.parseInt(id));
            if (user != null) {
                req.setAttribute("user", user);
                if (req.getSession().getAttribute("login") != null) {
                    if (editAll
                            || user.getLogin().equals(req.getSession().getAttribute("login"))) {
                        req.getRequestDispatcher("/WEB-INF/views/UserUpdateServlet.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect(String.format("%s/", req.getContextPath()));
                    }
                }
            }
        } else {
            req.setAttribute("user", new User("", "", "", new Timestamp(new Date().getTime()), new Role()));
            req.getRequestDispatcher("/WEB-INF/views/UserUpdateServlet.jsp").forward(req, resp);
        }
    }

    private List<Role> getRoles(boolean editAll) {
        return LOGIC.getRoles().
                stream().
                filter(e-> editAll || !e.isEditAll()).
                collect(Collectors.toList());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (!id.equals("0")) {
            User user = LOGIC.findById(Integer.parseInt(id));
            user.setName(req.getParameter("name"));
            user.setLogin(req.getParameter("login"));
            user.setEmail(req.getParameter("email"));
            Role role = LOGIC.getRoleById(Integer.parseInt(req.getParameter("role")));
            user.setRole(role);
            LOGIC.update(user);
        } else {
            LOGIC.add(new User(
                            req.getParameter("name"),
                            req.getParameter("login"),
                            req.getParameter("email"),
                            new Timestamp(new Date().getTime()),
                            LOGIC.getRoleById(Integer.parseInt(req.getParameter("role")))
                    )
            );
        }

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
