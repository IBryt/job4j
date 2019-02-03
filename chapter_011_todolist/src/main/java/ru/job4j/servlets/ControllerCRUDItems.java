package ru.job4j.servlets;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class ControllerCRUDItems extends HttpServlet {
    private SessionFactory factory;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String type = req.getParameter("type");
        if (type.equals("add")) {
            add(req);
        }
    }

    private void add(HttpServletRequest req) {
        final String desc = req.getParameter("desc");
        final Timestamp created = Timestamp.valueOf(req.getParameter("created").replace("T", " "));
        final boolean done = Boolean.parseBoolean(req.getParameter("done"));
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            Item item = new Item();
            item.setDesc(desc);
            item.setCreated(created);
            item.setDone(done);
            session.save(item);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
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
