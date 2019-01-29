package ru.job4j.chat.logic;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chat.StorageString;
import ru.job4j.chat.io.ConsoleOutput;
import ru.job4j.chat.io.Output;
import ru.job4j.chat.storage.Storage;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LogicTest {
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
    public void whenValueIsEmptyThenReturnFalse() throws IOException {
        assertThat(logic.add(""), is(false));
    }

    @Test
    public void whenValueIsNotEmptyThenReturnFalse() throws IOException {
        assertThat(logic.add("qwe"), is(true));
    }

    @Test
    public void whenAddedValueConstantSTOPValueThenReturnEmptyString() throws IOException {
        assertThat(logic.getRandomMsg(BasicLogic.STOP), is(""));
        assertThat(logic.getRandomMsg("some message"), is(""));
    }

    @Test
    public void whenAddedValueConstantCONTIValueThenReturnEmptyString() throws IOException {
        assertThat(logic.getRandomMsg(BasicLogic.STOP), is(""));
        assertThat(logic.getRandomMsg(BasicLogic.CONTINUE).isEmpty(), is(false));
    }
}