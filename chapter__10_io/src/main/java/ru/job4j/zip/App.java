package ru.job4j.zip;

import ru.job4j.Search;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Init and start program.
 */
public class App {

    private ParseArgs parseArgs;
    private Search search;
    private Package packaging;

    public App(String[] args) throws Exception {
        parseArgs = new ParseArgs(args);
        search = new Search();
        packaging = new Package();
        start();
    }

    private void start() throws IOException {
        List<File> files = search.files(parseArgs.getDirectory(), parseArgs.getExts());
        String output = packaging.execute(files, parseArgs.getDirectory(), parseArgs.getOutput());
        System.out.println(output);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        try {
            new App(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new Date());
    }
}
