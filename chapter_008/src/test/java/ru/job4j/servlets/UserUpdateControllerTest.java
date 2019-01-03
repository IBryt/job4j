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
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

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
@PrepareForTest(UserUpdateController.class)
public class UserUpdateControllerTest {
    private Validate service;
    //private Store store = DbStore.getInstance();
    private UserUpdateController controller = new UserUpdateController();
    private User user;

    @Before
    public void before() {
        service = new ValidateStub();
        Whitebox.setInternalState(UserUpdateController.class, "LOGIC", service);
        user = new User("test",
                "test",
                "test",
                new Timestamp(new Date().getTime()),
                new Role(1, "", true)
        );
        service.add(user);
    }

    @After
    public void after() {
        service.delete(user.getId());
    }

    @Test
    public void updateUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(request.getParameter("name")).thenReturn("test4");
        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("email")).thenReturn("test4");
        when(request.getParameter("role")).thenReturn("1");
        controller.doPost(request, response);
        assertThat(service.findAll().
                        entrySet().
                        stream().
                        anyMatch(entry -> entry.getValue().getEmail().equals("test4")),
                is(true)
        );
    }

    @Test
    public void addUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("email")).thenReturn("test");
        when(request.getParameter("role")).thenReturn("1");
        controller.doPost(request, response);
        assertThat(service.findAll().
                        entrySet().
                        stream().
                        anyMatch(entry -> entry.getValue().getLogin().equals("test")),
                is(true)
        );
    }
}