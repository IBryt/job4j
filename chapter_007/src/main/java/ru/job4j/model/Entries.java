package ru.job4j.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Entries {
    private List<Entry> entries;

    public Entries(List<Entry> entries) {
        this.entries = entries;
    }

    public Entries() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entries entries1 = (Entries) o;

        return entries != null ? entries.equals(entries1.entries) : entries1.entries == null;
    }

    @Override
    public int hashCode() {
        return entries != null ? entries.hashCode() : 0;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
