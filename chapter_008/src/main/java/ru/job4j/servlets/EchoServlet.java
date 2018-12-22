package ru.job4j.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.logic.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(EchoServlet.class.getName());
    private final ValidateService logic = ValidateService.getInstance();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        String login = req.getParameter("login");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        writer.append("hello world " + login);
        writer.flush();
    }
}