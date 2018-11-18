package magnit.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entry {
    private int value;

    public Entry() {

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

        return value == entry.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public Entry(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @XmlElement
    public void setValue(int value) {
        this.value = value;
    }
}
