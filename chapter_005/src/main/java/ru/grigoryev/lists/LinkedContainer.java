package ru.grigoryev.lists;

import java.util.Iterator;

/**
 * Implementation of linked list.
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 25.09.2017
 */
public class LinkedContainer<E> implements Iterable<E> {
    /**
     * The fictive node in the list represents the head of list.
     */
    private Node<E> head = null;
    /**
     * The last node in the list. If head == last the list is empty.
     */
    private Node<E> tail = head;
    /**
     * Number of elements in the list.
     */
    private int size = 0;

    /**
     * Default constructor.
     */
    public LinkedContainer() {
        this.head = new Node<E>();
        this.head.setNext(null);
        this.head.setPrivious(null);
        this.tail = head;
    }

    /**
     * Adds specified element to the list.
     *
     * @param value value to add
     */
    public void add(E value) {
        Node<E> node = new Node<>(value);
        tail.setNext(node);
        node.setPrivious(tail);
        tail = node;
        size++;
    }

    /**
     * Deletes element on specified position from the list.
     *
     * @param position specified position
     * @return true if operation succeeds and false otherwise
     */
    public boolean delete(int position) {
        boolean isDeleted = false;
        if (position < size) {
            Node<E> current = this.head.getNext();
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
            current.getPrivious().setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrivious(current.getPrivious());
            }
            current = null;
            size--;
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * Gets element in the specified position.
     *
     * @param position specified position of the element
     * @return the element in specified position or null if it doesn't exist
     */
    public E get(int position) {
        E result = null;
        Node<E> current = this.head.getNext();
        if (position < size) {
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
            result = current.getValue();
        }
        return result;
    }
    /**
     * Gets Node in the specified position.
     *
     * @param position specified position of the element
     * @return the Node in specified position or null if it doesn't exist
     */
    public Node<E> getNode(int position) {
        Node<E> current = this.head.getNext();
        if (position < size) {
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
        }
        return current;
    }
    /**
     * @return number of elements in the container
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks whether the list has cycle dependencies or not.
     * @return true if the the list has cycle dependencies and false otherwise
     */
    public boolean hasCycle() {
        Node<E> last = head;
        boolean hasNext = false;
        Node<E> current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
            if (tail.getNext() == current) {
                hasNext = true;
                break;
            }
        }
        return hasNext;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            /**
             * Current index.
             */
            private int position = 0;
            /**
             * Current node.
             */
            private Node<E> currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode.getNext() != null;
            }

            @Override
            public E next() {
                currentNode = currentNode.getNext();
                return currentNode.getValue();
            }
        };
    }
}
