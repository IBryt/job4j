package ru.job4j.controller;

import org.apache.logging.log4j.*;
import ru.job4j.logic.ValidateItem;
import ru.job4j.models.impl.Item;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.sql.Timestamp;

public class ControllerCRUDItems extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(ControllerCRUDItems.class.getName());
    private static final ValidateItem VALIDATE = ValidateItem.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        final String type = req.getParameter("type");
        if (type.equals("add")) {
            add(req);
        }
    }

    private void add(HttpServletRequest req) {
        VALIDATE.add(parseRequest(req));
    }

    private Item parseRequest(HttpServletRequest req) {
        final String desc = req.getParameter("desc");
        final Timestamp created = Timestamp.valueOf(req.getParameter("created").replace("T", " "));
        final boolean done = Boolean.parseBoolean(req.getParameter("done"));
        final Item item = new Item();
        item.setDesc(desc);
        item.setCreated(created);
        item.setDone(done);
        return item;
    }
}
