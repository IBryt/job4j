package ru.job4j.servlets;

import org.junit.Test;
import org.mockito.Mockito;
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

public class UserUpdateControllerTest {
    private ValidateService service = ValidateService.getInstance();
    //private Store store = DbStore.getInstance();
    private UserUpdateController controller = new UserUpdateController();

    @Test
    public void updateUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String login = "test3";
        service.add(new User("test4",
                        "test4",
                        "test4",
                        new Timestamp(new Date().getTime()),
                        new Role(1, "", true)
                )
        );
        User user = service.findByLogin("test4");
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(request.getParameter("name")).thenReturn("test4");
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("email")).thenReturn("test4");
        when(request.getParameter("role")).thenReturn("1");
        controller.doPost(request, response);
        assertThat(service.findAll().
                        entrySet().
                        stream().
                        anyMatch(entry -> entry.getValue().getLogin().equals(login)),
                is(true)
        );
    }

    @Test
    public void addUser() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String login = "test5";
        User user = new User(login,
                        "test5",
                        "test5",
                        new Timestamp(new Date().getTime()),
                        new Role(1, "", true)
        );
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(request.getParameter("name")).thenReturn("test5");
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("email")).thenReturn("test5");
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