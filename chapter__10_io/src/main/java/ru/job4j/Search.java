package ru.job4j;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class Search {
    private LinkedList<File> files = new LinkedList<>();
    private Queue<String> folders = new LinkedList<>();
    public List<File> files(String parent, List<String> exts) {
        OnlyExt onlyExt = new OnlyExt(exts);
        folders.add(parent);
        while (!folders.isEmpty()) {
            String path = folders.poll();
            File tmp = new File(path);
            if (tmp.isDirectory()) {
                for (File f : tmp.listFiles(onlyExt)) {
                    folders.offer(f.getAbsolutePath());
                }
            } else {
                //onlyExt.accept()
                files.add(tmp);
            }
        }
        return files;
    }

    private class OnlyExt implements FilenameFilter {
        private List<String> exts;

        public OnlyExt(List<String> exts) {
            this.exts = exts;
        }

        public boolean accept(File dir, String name) {
            File file = new File(dir + "/" + name);
            return exts.stream().anyMatch(s -> file.isDirectory() || name.endsWith(s));
        }
    }
}

