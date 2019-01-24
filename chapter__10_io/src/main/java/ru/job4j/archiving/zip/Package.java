package ru.job4j.archiving.zip;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class intended for create archives files.
 */
public class Package {

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
            zos.finish();
        }
        return pathTarget.getFileName().toAbsolutePath().toString();
    }

    private void addFile(ZipOutputStream zos, String pathZIP, Path pathFile) throws IOException {
        ZipEntry e = new ZipEntry(pathZIP);
        zos.putNextEntry(e);
        Files.copy(pathFile, zos);
        zos.closeEntry();
    }
}
