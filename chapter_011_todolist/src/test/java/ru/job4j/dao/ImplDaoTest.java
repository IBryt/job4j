package ru.job4j.dao;

import org.junit.Test;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.impl.Item;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
public class ImplDaoTest {
    private ImplDao<Item> dao = ImplDao.getInstance();

    @Test
    public void whenItemAddedUpdateAndRemovedThenTableMustBeEmpty() throws InterruptedException {
        final Item item = new Item();
        item.setDone(true);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("t");
        assertThat(dao.add(item).getId() != 0, is(true));
        item.setDesc("test");
        assertThat(dao.update(item).getDesc(), is("test"));
        final Item updated = dao.findByID(item.getId(), Item.class);
        assertThat(updated.getDesc(), is("test"));
        assertThat(dao.remove(item, Item.class), is(item));
        assertThat(dao.getAll(Item.class).contains(updated), is(false));
    }

    @Test(expected = Exception.class)
    public void whenItemUpdatedDifferentThreadsReturnException() throws InterruptedException {
        Item item = null;
        Item expected = null;
        try {
            item = dao.add(getItem());
            expected = dao.findByID(item.getId(), Item.class);
            final Item forException = dao.findByID(item.getId(), Item.class);
            execute(expected, "desc1");
            execute(forException, "desc2");
        } catch (Exception e) {
            assertThat(item, is(notNullValue()));
            item.setDesc("desc1");
            assertThat(item, is(expected));
            dao.remove(item, Item.class);
            throw e;
        }
    }

    private void execute(Item item, String value) throws InterruptedException {
        item.setDesc(value);
        dao.update(item);
    }

    private Item getItem() {
        final Item item = new Item();
        item.setDone(true);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("desc");
        return item;
    }
}