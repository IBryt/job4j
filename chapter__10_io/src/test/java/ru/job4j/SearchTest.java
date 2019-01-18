package ru.job4j;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.*;

public class SearchTest {

    String path = System.getProperty("java.io.tmpdir");

    @Test
    public void test() throws IOException {
        List<String> expected = new ArrayList<>();
        String parent = path + "\\parent";
        new File(parent).mkdirs();
        new File(parent + "\\child1").mkdirs();
        new File(parent + "\\child2").mkdirs();
        new File(parent + "\\child1\\child3").mkdirs();
        File file = new File(parent + "\\child1\\child3\\test.txt");
        file.createNewFile();
        expected.add(file.getAbsolutePath());
        new File(parent + "\\child1\\child3\\test.tx").createNewFile();
        File myFolder = new File(parent);
        File[] files1 = myFolder.listFiles();
        Search search = new Search();
        List<String> exts = new ArrayList<>();
        exts.add(".html");
        exts.add(".txt");
        System.getProperty("java.io.tmpdir");
        ArrayList<String> list = search.files(parent, exts).
                stream().
                map(File::getAbsolutePath).
                collect(Collectors.toCollection(ArrayList::new));
        assertThat(list.toArray(), arrayContainingInAnyOrder(expected.toArray()));
    }
}