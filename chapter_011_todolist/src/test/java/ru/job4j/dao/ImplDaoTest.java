package ru.job4j.dao;

import org.junit.Test;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.impl.Item;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
public class ImplDaoTest {
    private ImplDao<Item> dao = ImplDao.getInstance();

    @Test
    public void whenItemAddedUpdateAndRemovedThenTableMustBeEmpty() {
        final Item item = new Item();
        item.setDone(true);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("t");
        assertThat(dao.add(item).getId() != 0, is(true));
        item.setDesc("test");
        assertThat(dao.update(item).getDesc(), is("test"));
        assertThat(dao.findByID(item.getId(), Item.class).getDesc(), is("test"));
        assertThat(dao.remove(item, Item.class), is(item));
        assertThat(dao.getAll(Item.class).size(), is(0));
    }
}