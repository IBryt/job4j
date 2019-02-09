package ru.job4j.controller;

import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dao.ImplDao;
import ru.job4j.models.impl.Item;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ControllerItemsTest {
    private final ControllerItems controller = new ControllerItems();

    @Test
    public void whetGetRequestReturnJson() throws ServletException, IOException {
//        HttpServletRequest req = mock(HttpServletRequest.class);
//        HttpServletResponse resp = mock(HttpServletResponse.class);
//        Mockito.when(req.getParameter("done")).thenReturn(String.valueOf(true));
//        controller.doGet(req,resp);
    }
}