package ru.job4j.models;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private long version;

    public Model(int id) {
        this.id = id;
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Model model = (Model) o;

        if (id != model.id) {
            return false;
        }
        return version == model.version;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (version ^ (version >>> 32));
        return result;
    }
}
