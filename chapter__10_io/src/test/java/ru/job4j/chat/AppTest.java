package ru.job4j.chat;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chat.io.ConsoleOutput;
import ru.job4j.chat.io.Input;
import ru.job4j.chat.io.Output;
import ru.job4j.chat.io.StubInput;
import ru.job4j.chat.logic.Logic;
import ru.job4j.chat.storage.Storage;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AppTest {

    StubInput input;
    Logic logic;
    Output output;

    @Before
    public void before() throws IOException {
        Storage storage = new StorageString();
        logic = new Logic(storage);
        input = new StubInput(logic);
        input.setSends(new String[]{"some", Logic.STOP, "some", Logic.CONTINUE, Logic.CLOSE});
        output = new ConsoleOutput(logic);
        App app = new App(input, logic, output);
        app.start();
    }

    @Test
    public void whenStartApp() throws IOException {
        List<String> allMessages = logic.getAllMessages();
        assertThat(allMessages.size(), is(7));
        assertThat(allMessages.get(0), is("some"));
        assertThat(allMessages.get(2), is(Logic.STOP));
        assertThat(allMessages.get(3), is("some"));
        assertThat(allMessages.get(4), is(Logic.CONTINUE));
        assertThat(allMessages.get(6), is(Logic.CLOSE));
    }

}