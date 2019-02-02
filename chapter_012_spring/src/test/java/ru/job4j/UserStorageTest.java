package ru.job4j;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddUserToStorageShouldSafeIt() {
        final MemoryStorage storage = new MemoryStorage();
        final UserStorage userStorage = new UserStorage(storage);
        storage.add(new User());
    }
    @Test
    public void whenLoadContextShouldGetBean() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        final UserStorage memory = context.getBean(UserStorage.class);
        //memory.add(new User());
        assertNotNull(memory);
    }
}