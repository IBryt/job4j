package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private ValidateService service = ValidateService.getInstance();
    private UserController controller = new UserController();
    @Before
    public void before() {
        service.add(
                new User("test1",
                        "test1",
                        "test1",
                        new Timestamp(new Date().getTime()),
                        new Role(1, "", true)
                )
        );
    }

    @Test
    public void deleteUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        assertThat(service.findAll().
                        entrySet().
                        stream().
                        anyMatch(entry -> entry.getValue().getLogin().equals("test1")),
                is(true)
        );
        User user = service.findByLogin("test1");
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        controller.doPost(request, response);
        assertThat(service.findAll().
                entrySet().stream().
                anyMatch(entry -> entry.getValue().getLogin().equals("test1")),
                is(false)
        );
    }
}