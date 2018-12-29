package ru.job4j.servlets;

import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserCreateControllerTest {
    private ValidateService service = ValidateService.getInstance();
    private UserCreateController controller = new UserCreateController();

    @Test
    public void addUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String login = "test2";
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("name")).thenReturn(login);
        when(request.getParameter("login")).thenReturn("test2");
        when(request.getParameter("email")).thenReturn("test2");
        when(request.getParameter("role")).thenReturn("1");
        controller.doPost(request, response);
        assertThat(service.findAll().
                entrySet().
                stream().
                anyMatch(entry -> entry.getValue().getLogin().equals(login)),
                is(true)
        );
    }
}