package ru.job4j.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entry {
    private int id;
    private String name;
    private String text;
    private String link;

    public Entry() {

    }

    public Entry(int id, String name, String text, String link) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entry entry = (Entry) o;

        if (id != entry.id) {
            return false;
        }
        if (name != null ? !name.equals(entry.name) : entry.name != null) {
            return false;
        }
        if (text != null ? !text.equals(entry.text) : entry.text != null) {
            return false;
        }
        return link != null ? link.equals(entry.link) : entry.link == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entry{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
