package ru.job4j.bot.server;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerTest {
    private final static String LS = System.lineSeparator();

    @Test
    public void whenSendExitThenServerShutdown() throws IOException {
        testServer("exit", Joiner.on(LS).join("wait command ...", LS));
    }


    @Test
    public void whenSendHelloThenResponseWelcomeMessage() throws IOException {
        testServer(
                Joiner.on(LS).join("hello", "exit"),
                Joiner.on(LS).join(
                        "wait command ...",
                        "",
                        "Hello, dear friend, I'm a oracle.",
                        "",
                        "wait command ...",
                        LS
                )
        );
    }

    private void testServer(String input, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        Server server = new Server(socket);
        server.start();
        assertThat(out.toString(), is(expected));
    }
}