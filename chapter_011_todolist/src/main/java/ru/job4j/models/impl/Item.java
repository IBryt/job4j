package ru.job4j.models.impl;

import ru.job4j.models.Model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Item extends Model {

    private String desc;
    private Timestamp created;
    private boolean done;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item{"
               + "desc='" + desc + '\''
               + ", created=" + created
               + ", done=" + done + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (done != item.done) {
            return false;
        }
        if (desc != null ? !desc.equals(item.desc) : item.desc != null) {
            return false;
        }
        return created != null ? created.equals(item.created) : item.created == null;
    }

    @Override
    public int hashCode() {
        int result = desc != null ? desc.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
