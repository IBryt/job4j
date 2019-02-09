package ru.job4j.logic;

import org.apache.logging.log4j.*;
import ru.job4j.dao.ImplDao;
import ru.job4j.models.impl.Item;

import java.util.*;

public class ValidateItem {
    private static final Logger LOG = LogManager.getLogger(ValidateItem.class.getName());
    private final static ImplDao DAO = ImplDao.getInstance();
    private final static ValidateItem INSTANCE = new ValidateItem();

    private ValidateItem() {
        super();
    }

    public static ValidateItem getInstance() {
        return INSTANCE;
    }

    public void add(Item item) {
        DAO.add(item);
    }

    public List<Item> getAll(boolean done) {
        ArrayList<Item> list = new ArrayList<>();
        if (done) {
            list = (ArrayList<Item>) DAO.getAll(Item.class);
        } else {
            final HashMap<String, Object> sectionWhere = new HashMap<>();
            sectionWhere.put("done", done);
            list = (ArrayList<Item>) DAO.getAll(Item.class, sectionWhere);
        }
        return list;
    }

    public void update(Item item) {
        DAO.update(item);
    }

    public Item findByID(int id) {
        return (Item) DAO.findByID(id, Item.class);
    }
}
