package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
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

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(UserCreateController.class)
public class UserCreateControllerTest {
    private Validate service;
    private UserCreateController controller = new UserCreateController();

    @Before
    public void before() {
        service = new ValidateStub();
        Whitebox.setInternalState(UserCreateController.class, "LOGIC", service);
        service.add(
                new User("test1",
                        "test1",
                        "test1",
                        new Timestamp(new Date().getTime()),
                        new Role(1, "", true)
                )
        );
    }

    @After
    public void after() {
        User user = service.findByLogin("test1");
        service.delete(user.getId());
    }

    @Test
    public void addUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String login = "test1";
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("name")).thenReturn(login);
        when(request.getParameter("login")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("test1");
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