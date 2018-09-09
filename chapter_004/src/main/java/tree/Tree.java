package tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;
    private int size = 0;

    public Tree(E i) {
        root = new Node<>(i, null);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean res = false;
        if (parent != null && child != null) {
            Optional<Node<E>> entry = this.findBy(parent);
            if (entry.isPresent() && !this.findBy(child).isPresent()) {
                entry.get().add(new Node<E>(child, parent));
                res = true;
                size++;
            }
        }
        return res;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    public boolean isBinary() {
        boolean res = true;
        for (E e : this) {
            if (findBy(e).get().leaves().size() > 2) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node curr = root;
            private int modCount = size;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public E next() {
                checkException();
                Node<E> old = curr;
                if (curr.leaves().size() != 0) {
                    curr = (Node) curr.leaves().get(0);
                } else {
                    Optional<Node<E>> parent = findBy((E) curr.getParent());
                    List<Node<E>> list = parent.get().leaves();
                    int index = list.indexOf(curr) + 1;
                    curr = index >= list.size() ? null : list.get(index);
                }
                return old == null ? null : (E) old.getValue();
            }

            private void checkException() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (size != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}