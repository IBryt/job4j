package ru.job4j.chat.io;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chat.StorageString;
import ru.job4j.chat.logic.Logic;
import ru.job4j.chat.storage.Storage;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConsoleOutputTest {
    Storage storage;
    Logic logic;
    Output output;

    @Before
    public void before() throws IOException {
        storage = new StorageString();
        logic = new Logic(storage);
        output = new ConsoleOutput(logic);
    }

    @Test
    public void whenDisplayNoEmptyValueThenReturnTrue() {
        assertThat(output.display("test"), is(true));
    }

    @Test
    public void whenDisplayEmptyValueThenReturnFalse() {
        assertThat(output.display(""), is(false));
    }

    @Test
    public void whenSendEmptyStringReturnEmptyResponse() throws IOException {
        assertThat(output.getResponse(""), is(""));
    }

    @Test
    public void whenSendSomeMessageReturnSomeResponse() throws IOException {
        assertThat(output.getResponse("Some message").isEmpty(), is(false));
    }

    @Test
    public void whenSendConstantCLOSEReturnSomeResponse() throws IOException {
        assertThat(output.getResponse(Logic.CLOSE), is(""));
    }
}