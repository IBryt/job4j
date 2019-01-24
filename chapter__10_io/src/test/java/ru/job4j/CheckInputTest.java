package ru.job4j;
import org.junit.Test;
import ru.job4j.archiving.CheckInput;

import java.io.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CheckInputTest {
    private CheckInput in = new CheckInput();

    @Test(expected = IOException.class)
    public void returnTrueIfEvenNumberElseFalse() throws IOException {
        try (InputStream in2 = new DataInputStream(new ByteArrayInputStream("0002".getBytes()))) {
            assertThat(in.isNumber(in2), is(true));
        }
        try (InputStream in2 = new DataInputStream(new ByteArrayInputStream("0001".getBytes()))) {
            assertThat(in.isNumber(in2), is(false));
        }
        try (InputStream in2 = new DataInputStream(new ByteArrayInputStream("1".getBytes()))) {
            assertThat(in.isNumber(in2), is(false));
        }
    }

    @Test
    public void returnStreamWithRemoveAbuses() throws IOException {
        String tmp = "тест test tes t те"
                + System.lineSeparator()
                + "st te st te"
                + System.lineSeparator();
        String expected = "tes t те"
                + System.lineSeparator()
                + "st te st te"
                + System.lineSeparator();
        String[] abuse = new String[2];
        abuse[0] = "test ";
        abuse[1] = "тест ";
        ByteArrayInputStream arrayInput = new ByteArrayInputStream(tmp.getBytes());
        ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
        try (InputStreamReader input = new InputStreamReader(arrayInput);
             OutputStreamWriter output = new OutputStreamWriter(arrayOutput)) {
            in.dropAbuses(input, output, abuse);
            assertThat(arrayOutput.toString(), is(expected));
        }
    }
}