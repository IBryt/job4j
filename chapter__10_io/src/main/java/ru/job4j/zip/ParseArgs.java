package ru.job4j.zip;
import org.apache.commons.cli.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parse main parameters with arguments.
 */
public class ParseArgs {
    private String[] args;
    private String repo;
    private String directory;
    private List<String> exts;
    private String output;

    public ParseArgs(String[] args) throws ParseException {
        this.args = args;
        parseArgs();
    }

    private void parseArgs() throws ParseException {
        Options options = new Options();
        options.addOption("r", "repo", true, "path packaging folders");
        options.addOption("d", "directory", true, "directory for packaging");
        options.addOption("e", "exts", true, "file extensions");
        options.addOption("o", "output", true, "path file output");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        this.repo = cmd.getOptionValue("r");
        this.directory = Paths.get(cmd.getOptionValue("d")).toAbsolutePath().toString();
        this.exts = setExts(cmd.getOptionValue("e"));
        this.output = cmd.getOptionValue("o");
    }

    private List<String> setExts(String e) {
        ArrayList<String> list;
        return Arrays.stream(e.split(",")).map(v -> v.startsWith(".") ? v : "." + v).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getRepo() {
        return repo;
    }

    public String getDirectory() {
        return directory;
    }

    public List<String> getExts() {
        return exts;
    }

    public String getOutput() {
        return output;
    }
}
