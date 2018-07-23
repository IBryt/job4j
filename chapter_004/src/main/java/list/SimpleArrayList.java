package list;

import java.util.NoSuchElementException;

/**
 * Класс SimpleArrayList.
 */
public class SimpleArrayList<E> {

    protected int size;
    protected Node<E> first;


    /**
     * Метод вставляет в начало списка данные.
     */
    public void add(E date) {
        Node<E> newLink = new Node<>(date);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }

    /**
     * Реализовать метод удаления первого элемент в списке.
     */
    public E delete() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        return unlinkFirst(f);
    }

    E unlinkFirst(Node<E> f) {
        final E element = f.date;
        f.date = null;
        this.first = f.next;
        size--;
        return element;
    }
    /**
     * Метод получения элемента по индексу.
     */
    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.date;
    }

    /**
     * Метод получения размера коллекции.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Класс предназначен для хранения данных.
     */
    public static class Node<E> {

        E date;
        Node<E> next;

        Node(E date) {
            this.date = date;
        }
    }
}
