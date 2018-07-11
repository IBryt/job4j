package ru.job4j.sort;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Department {

    public Set sort(Set source) {
        Set<String> result = new TreeSet<String>();
        result.addAll(source);
        result.addAll(getMissedDepartment(source));
        return result;
    }
    public Set sortReverse(Set source) {
        Set<String> result = new TreeSet<String>(Comparator.reverseOrder());
        result.addAll(source);
        result.addAll(getMissedDepartment(source));
        return result;
    }

    private Set getMissedDepartment(final Set<String> source) {
        Set<String> result = new HashSet<>();
        for (String str : source) {
            for (int i = 0; i != str.length(); i++) {
                if (str.charAt(i) == '\\') {
                    String department = str.substring(0, i);
                    if (!source.contains(department)) {
                        result.add(department);
                    }
                }
            }
        }
        return result;
    }
}
