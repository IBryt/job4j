package ru.job4j.controller;

import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
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