package ru.job4j.bot.client;

import ru.job4j.bot.Config;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private Input input;

    public Client(Socket socket, Input input) {
        this.socket = socket;
        this.input = input;
    }

    public void start() throws IOException {

        String str = "";
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
             BufferedReader in = new BufferedReader(inReader)) {
            do {
                str = in.readLine();
                while (!str.isEmpty()) {
                    System.out.println(str);
                    str = in.readLine();
                }
                str = input.read();
                out.println(str);
            } while (!str.equals("exit"));
        }
    }

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        final int port = Integer.parseInt(config.get("port"));
        final String host = config.get("host");
        ConsoleInput console = new ConsoleInput();
        try (final Socket socket = new Socket(InetAddress.getByName(host), port)) {
            new Client(socket, console).start();
        }
    }
}
