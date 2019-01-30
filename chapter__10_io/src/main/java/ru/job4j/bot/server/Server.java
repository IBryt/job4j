package ru.job4j.bot.server;


import ru.job4j.bot.Config;

import java.io.*;
import java.net.*;

public class Server {

    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void start() throws IOException {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String ask = "";
            do {
                out.println("wait command ...");
                out.println();
                ask = in.readLine();
                //System.out.println(ask);
                if ("hello".equals(ask)) {
                    out.println("Hello, dear friend, I'm a oracle.");
                    out.println();
                }
            } while (!ask.equals("exit"));
        }
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(new Config().get("port"));
        try (final Socket socket = new ServerSocket(port).accept()) {
            new Server(socket).start();
        }
    }
}
