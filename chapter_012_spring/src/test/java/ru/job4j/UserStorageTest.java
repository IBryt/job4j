package ru.job4j;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddUserToMemoryStorageShouldSafeItAndShow() {
        testForDifferentStorage(MemoryStorage.class);
    }

    @Test
    public void whenAddUserToJdbcStorageShouldSafeItAndShow() {
//        testForDifferentStorage(JdbcStorage.class);
    }

    private void testForDifferentStorage(Class classStorage) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        final UserStorage userStorage = (UserStorage) context.getBean("userStorage");
        final User user = new User(1, "username");
        final User[] users = new User[1];
        users[0] = user;
        userStorage.add(user);
        assertNotNull(userStorage);
        assertThat(userStorage.getAll().size(), is(users.length));
        assertThat(userStorage.getAll().toArray(), is(users));
    }
}