package ru.job4j;
import org.junit.Test;
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
}