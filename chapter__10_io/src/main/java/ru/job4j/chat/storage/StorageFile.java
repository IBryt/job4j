package ru.job4j.chat.storage;

import ru.job4j.chat.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

public class StorageFile implements Storage {
    private String pathWriter;
    private String pathRandomMsg;
    public StorageFile() {
        Config config = new Config();
        pathWriter = config.get("storage");
        pathRandomMsg = config.get("responses");
        try {
            Files.deleteIfExists(Paths.get(pathWriter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(final String value) throws IOException {
        try (FileChannel channel  = (FileChannel) Files.newByteChannel(Paths.get(pathWriter), WRITE, APPEND, CREATE)) {
            String tmp = value + System.lineSeparator();
            ByteBuffer buffer = ByteBuffer.allocate(tmp.getBytes().length);
            buffer.put(tmp.getBytes());
            buffer.rewind();
            channel.write(buffer);
        }
    }

    @Override
    public List<String> getRandomMsg() throws IOException {
        List<String> list;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathRandomMsg))) {
            list = reader.lines().filter(s-> s.intern() != "").collect(Collectors.toList());
        }
        return list;
    }
}
