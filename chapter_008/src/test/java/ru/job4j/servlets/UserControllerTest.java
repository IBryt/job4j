package ru.job4j.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserControllerTest {
    private Validate service;
    private UserController controller = new UserController();
    @Before
    public void before() {
        service = new ValidateStub();
        mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(service);
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