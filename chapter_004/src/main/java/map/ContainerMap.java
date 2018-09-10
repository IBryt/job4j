package map;

import java.util.*;

public class ContainerMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    private int size = 0;
    private int threshold;
    private Node<K, V>[] table;
    private Set<Map.Entry<K, V>>  entrySet;

    public ContainerMap(int initialCapacity) {
        this.threshold = initialCapacity;
    }

    public ContainerMap() {
        this.threshold = DEFAULT_INITIAL_CAPACITY;
    }

    private static class Node<K, V>  implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }
        public final V getValue() {
            return value;
        }
        public final String toString() {
            return key + "=" + value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node<?, ?> node = (Node<?, ?>) o;

            if (hash != node.hash) {
                return false;
            }
            if (key != null ? !key.equals(node.key) : node.key != null) {
                return false;
            }
            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            int result = hash;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    public boolean insert(K key, V value) throws UnsupportedOperationException {
        return putVal(hash(key), key, value) != null;
    }

    private static final int hash(Object key) {
        int h = key.hashCode();
        return (key == null) ? 0 : h ^ (h >>> 16);
    }

    private final V putVal(int hash, K key, V value) throws UnsupportedOperationException {
        V result = null;
        Node<K, V>[] tab = table;
        int n = tab == null ? 0 : tab.length;
        int i = (n - 1) & hash;
        if (tab == null || n == 0) {
            tab = resize();
            n = (tab).length;
            i = (n - 1) & hash;
        }
        if ((tab[i]) == null) {
            tab[i] = new Node<>(hash, key, value, null);
            result = value;
            if (++size >= threshold) {
                resize();
            }
        } else if (tab[i].hash == hash && tab[i].key.equals(key)) {
            tab[i].value = value;
            result = value;
        } else {
            throw new UnsupportedOperationException();
        }
        return result;
    }

    private Node<K, V>[] resize() throws UnsupportedOperationException {
        Node<K, V>[] oldTab = null;
        int oldThreshold = threshold;
        if (table != null) {
            threshold = threshold << 1;
            size = 0;
            oldTab = table;
        }
        table = (Node<K, V>[]) new Node[threshold];
        if (oldTab != null) {
            for (int i = 0; i != oldThreshold; i++) {
                if (oldTab[i] != null) {
                    insert(oldTab[i].key, oldTab[i].value);
                }
            }
        }
        return table;
    }

    public V get(K key) {
        Node<K, V> e = getNode(hash(key), key);
        return e == null ? null : e.value;
    }

    private final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab = table;
        int n = tab == null ? 0 : tab.length;
        Node<K, V> first = tab[(n - 1) & hash];
        Node<K, V> result = null;
        K k;
        if (tab != null && n > 0 && first != null) {
            k = first.key;
            if (first.hash == hash
                    && (k == key || (key != null && key.equals(k)))) {
                result = first;
            }
        }
        return result;
    }

    public boolean delete(K key) {
        return removeNode(hash(key), key);
    }

     private final boolean removeNode(int hash, Object key) {
        Node<K, V>[] tab = table;
        int n = tab == null ? 0 : tab.length;
        int index = (n - 1) & hash;
        Node<K, V> p = tab == null ? null : tab[index];
        K k = p == null ? null : p.key;
        boolean result = false;
        if (tab != null && n > 0
                && p != null) {
            if (p.hash == hash
                    && (k == key || (key != null && key.equals(k)))) {
                table[index] = null;
                result = true;
            }
        }
        return result;
    }

    public int size() {
        return size;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new EntrySet();
        }
        return entrySet;
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        public final int size()  {
            return size;
        }

        public final void clear() {
            this.clear();
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }
    }

    final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            return nextNode();
        }
    }
    abstract class HashIterator {
        Node<K, V> next;        // next entry to return
        Node<K, V> current;     // current entry
        int index;             // current slot
        int modCount;

        HashIterator() {
            Node<K, V>[] t = table;
            current = next;
            next = null;
            index = 0;
            modCount = size;
            if (t != null && size > 0) { // advance to first entry
                do {
                    if (index < t.length) {
                        next = t[index++];
                    }
                } while (index < t.length && t[index - 1] == null);
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final Node<K, V> nextNode() {
            Node<K, V>[] t;
            Node<K, V> e = next;
            if (modCount != size) {
                throw new ConcurrentModificationException();
            }
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = current.next;
            if (next == null && table != null) {
                do {
                    if (index < table.length) {
                        next = table[index++];
                    }
                } while (index < table.length && table[index - 1] == null);
            }
            return e;
        }
    }
}