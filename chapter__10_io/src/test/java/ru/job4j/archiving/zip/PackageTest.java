package ru.job4j.archiving.zip;

import org.apache.logging.log4j.core.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.archiving.Search;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.*;

public class PackageTest {
    private final String parentPath = "src/test/java/ru/job4j/archiving/zip/";
    private final String outputPath = "test.zip";
    private final String currentPath = "src/test/java/ru/job4j/archiving/zip/PackageTest.java";
    private final String ext = ".java";
    private final String strArgs  = String.format("-r pack.jar -d src/test/java/ -e java -o %s", outputPath);
    private List<File> files;
    private Path pathToAppend;

    @Before
    public void before() {
        files = Arrays.asList(new File(currentPath));
        pathToAppend = Paths.get("src/test/java/").toAbsolutePath();
    }

    @Test
    public void comparisonStringsCurrentFileWithFileInArchive() throws IOException {
        String res;
        String expected;
        String path = new Package().execute(files, parentPath, outputPath);
        try (ZipFile zipFile = new ZipFile(path);
            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry("PackageTest.java"));
            InputStreamReader inReader = new InputStreamReader(inputStream)) {
            res = IOUtils.toString(inReader);
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(currentPath))) {
            expected = IOUtils.toString(reader);
        }
        Files.deleteIfExists(Paths.get(path));
        assertThat(res,  is(expected));
    }

    @Test
    public void comparingFileStructuresWithArchive() throws Exception {
        String[] args = strArgs.split(" ");
        List<String> list = new ArrayList<>();
        new App(args);
        Path pathTarget = Paths.get(outputPath).toAbsolutePath();
        try (ZipFile zipFile = new ZipFile(pathTarget.toString())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(ext)) {
                    list.add(pathToAppend.resolve(entry.getName()).toString());
                }
            }
        }
        Search search = new Search();
        ArrayList<String> expected  = search.files(
                new File(pathToAppend.toString()).getAbsolutePath(), Arrays.asList(ext)).
                stream().
                map(File::getAbsolutePath).
                collect(Collectors.toCollection(ArrayList::new));
        Files.deleteIfExists(pathTarget);
        assertThat(list.toArray(), arrayContainingInAnyOrder(expected.toArray()));
    }
}