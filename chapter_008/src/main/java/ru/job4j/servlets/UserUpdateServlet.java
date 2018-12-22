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

public class UserUpdateServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserUpdateServlet.class.getName());
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        User user = logic.findById(id);
        if (user == null) {
            writer.append("пользователь не найден");
            writer.flush();
            return;
        }
        StringBuilder builder = new StringBuilder("<table>");
        builder.append("<form action=" + req.getContextPath() + "/edit?id=" + user.getId() + " method='post'>")
                .append("<input type='hidden' name='action' value='update'><br>")
                .append("id <input type='text' name='id' value=" + user.getId() + " disabled><br>")
                .append("name <input type='text' name='name' value=" + user.getName() + "><br>")
                .append("login <input type='text' name='login' value=" + user.getLogin() + "><br>")
                .append("email <input type='text' name='email' value=" + user.getEmail() + "><br>")
                .append("date <input type='text' name='email' value=" + user.getCreateDate().toString() + " disabled><br>")
                .append("<input type='submit' value='submit'>")
                .append("</table>");
        writer.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Title</title>\n"
                + "</head>\n"
                + "<body>\n"
                + builder.toString()
                + "</body>\n"
                + "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = logic.findById(id);
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        logic.update(user);
        doGet(req, resp);
    }
}
