package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ControllerItems extends HttpServlet {
    private SessionFactory factory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        boolean done = Boolean.valueOf(req.getParameter("done"));
        List<Item> items = new ArrayList();
        try {
            Criteria criteria = session.createCriteria(Item.class);
            if (!done) {
                criteria.add(Restrictions.eq("done", done));
            }
            items = criteria.list();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
        String json = new ObjectMapper().writeValueAsString(items);
        try (final OutputStreamWriter streamWriter = new OutputStreamWriter(resp.getOutputStream(), "utf-8");
             final PrintWriter writer = new PrintWriter(streamWriter)) {
            writer.append(json);
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        factory.close();
    }

    @Override
    public void init() throws ServletException {
        factory = new Configuration().configure().buildSessionFactory();
    }
}
