package ru.job4j.archiving.zip;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class intended for create archives files.
 */
public class Package {
    /**
     *  size buffer in bytes.
     */
    private static final int SIZE_BUFFER = 4096;

    /**
     * Create archive file with <code>files</code> of preserving relative paths relatively <code>parentPath</code>.
     *
     * @param files List of files to be archived.
     * @param parentPath absolute path from which files are selected.
     * @param outputPath path created archive.
     * @return path created archive.
     * @throws IOException If an I/O error occurs.
     */
    public String execute(List<File> files, String parentPath, String outputPath) throws IOException {
        Path pathTarget = Paths.get(outputPath);
        Files.deleteIfExists(pathTarget);
        try (OutputStream os = Files.newOutputStream(pathTarget, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
             ZipOutputStream zos = new ZipOutputStream(os)) {
            for (File file : files) {
                Path pathFile = Paths.get(file.getPath());
                String pathZIP = Paths.get(parentPath).relativize(pathFile).toString();
                addFile(zos, pathZIP, pathFile);
            }
            zos.closeEntry();
        }
        return pathTarget.getFileName().toAbsolutePath().toString();
    }

    private void addFile(ZipOutputStream zos, String pathZIP, Path pathFile) throws IOException {
        int count;
        try (SeekableByteChannel channel = Files.newByteChannel(pathFile)) {
            ZipEntry e = new ZipEntry(pathZIP);
            zos.putNextEntry(e);
            ByteBuffer buffer = ByteBuffer.allocate(SIZE_BUFFER);
            do {
                count = channel.read(buffer);
                if (count != -1) {
                    buffer.rewind();
                    byte[] bytes = buffer.array();
                    if (count != bytes.length) {
                        bytes = Arrays.copyOf(bytes, count);
                    }
                    zos.write(bytes);
                }
            } while (count != -1);
        }
    }
}
