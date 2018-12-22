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

public class UserCreateServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserCreateServlet.class.getName());
    private final ValidateService logic = ValidateService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder("<table>");
                builder.append("<form action=" + req.getContextPath() + "/create  method='post'>")
                .append("<input type='hidden' name='action' value='add'><br>")
                .append("name <input type='text' name='name' value=''><br>")
                .append("login <input type='text' name='login' value=''><br>")
                .append("email<input type='text' name='email' value=''><br>")
                .append("<input type='submit' value='submit'>")
                .append("</table>");
        PrintWriter writer = resp.getWriter();
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
        operations(req);
        doGet(req, resp);
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
