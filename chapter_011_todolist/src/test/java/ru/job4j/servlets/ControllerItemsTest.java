package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.io.IOException;

import static org.mockito.Mockito.when;

public class ControllerItemsTest {
    private ControllerItems controller = new ControllerItems();

    @Before
    public void before() throws ServletException {
        controller.init();
    }

    @After
    public void after() {
        controller.destroy();
    }

    @Test
    public void whenParameterEqualDoneThenReturnOnlyDoneItems() throws ServletException, IOException {
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        when(request.getParameter("done")).thenReturn("false");
//        //when(response.getOutputStream()).thenReturn();
//        controller.doGet(request, response);
    }
}