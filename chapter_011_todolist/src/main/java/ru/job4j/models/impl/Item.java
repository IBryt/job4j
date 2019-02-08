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
}
