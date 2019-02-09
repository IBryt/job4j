package ru.job4j.controller;

import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dao.ImplDao;
import ru.job4j.models.impl.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ControllerCRUDItemsTest {
    private final ControllerCRUDItems controller = new ControllerCRUDItems();
    private final ImplDao<Item> dao = ImplDao.getInstance();

    private Item getItem() {
        final Item item = new Item();
        item.setDone(true);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("test");
        return item;
    }

    @Test
    public void whenAddedItemThenExistInBase() throws ServletException {
        final Item item = getItem();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        final String desc = item.getDesc();
        final String created = String.valueOf(item.getCreated());
        final String done = String.valueOf(item.isDone());
        Mockito.when(req.getParameter("type")).thenReturn("add");
        Mockito.when(req.getParameter("desc")).thenReturn(desc);
        Mockito.when(req.getParameter("created")).thenReturn(created);
        Mockito.when(req.getParameter("done")).thenReturn(done);
        controller.doPost(req, resp);
        final Item res = dao.getAll(Item.class).get(0);
        assertThat(item.getDesc(), is(res.getDesc()));
        assertThat(item.getCreated(), is(res.getCreated()));
        assertThat(item.isDone(), is(res.isDone()));
        dao.remove(res, Item.class);
    }

    @Test
    public void whenUpdateItemThenChangeFieldInItem() throws ServletException {
        final Item item = dao.add(getItem());
        final boolean expected = !item.isDone();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("type")).thenReturn("update");
        Mockito.when(req.getParameter("id")).thenReturn(String.valueOf(item.getId()));
        Mockito.when(req.getParameter("done")).thenReturn(String.valueOf(expected));
        controller.doPost(req, resp);
        assertThat(!item.isDone(), is(expected));
        dao.remove(item, Item.class);
    }
}