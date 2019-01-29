package ru.job4j.chat.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

/**
 * Implement the storage in the file.
 */
public class StorageFile extends AbstractStorage implements Storage {

    public StorageFile() throws IOException {
        super();
    }

    @Override
    public String add(final String value) throws IOException {
        try (FileChannel channel  = (FileChannel) Files.newByteChannel(Paths.get(storage), WRITE, APPEND, CREATE)) {
            String tmp = value + System.lineSeparator();
            ByteBuffer buffer = ByteBuffer.allocate(tmp.getBytes().length);
            buffer.put(tmp.getBytes());
            buffer.rewind();
            channel.write(buffer);
        }
        return value;
    }

    @Override
    public List<String> getAllMessages() throws IOException  {
        List<String> collect = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(storage))) {
           collect = reader.lines().collect(Collectors.toList());
        }
        return collect;
    }
}
