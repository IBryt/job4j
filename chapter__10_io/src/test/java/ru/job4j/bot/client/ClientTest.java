package ru.job4j.bot.client;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {
    private final static String LS = System.lineSeparator();

    @Test
    public void whenSendExitThenClientShutdown() throws IOException {
        testClient(
                LS,
                Joiner.on(LS).join("exit", ""),
                Joiner.on(LS).join("exit", "")
        );
    }
    @Test
    public void whenSendHelloThenResponseWelcomeMessage() throws IOException {
        testClient(
                Joiner.on(LS).join("", "Hello, dear friend, I'm a oracle.", LS),
                Joiner.on(LS).join("hello", "exit"),
                Joiner.on(LS).join("hello", "exit", "")
        );
    }

    private void testClient(String resp, String input, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(resp.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        Client client = new Client(socket, new Input() {
            String[] strings = input.split(LS);
            int index = 0;
            @Override
            public String read() {
                return strings[index++];
            }
        });
        client.start();
        assertThat(out.toString(), is(expected));
    }
}