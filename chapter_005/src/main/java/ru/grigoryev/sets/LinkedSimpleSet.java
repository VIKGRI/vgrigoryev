package ru.grigoryev.sets;

import ru.grigoryev.lists.Node;

import java.util.Iterator;

/**
 * Implementation of resizable set.
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 26.09.2017
 */
public class LinkedSimpleSet<E> implements Iterable<E> {
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
    public LinkedSimpleSet() {
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
        boolean doesContain = false;
        Node<E> current = this.head;
        if (this.size != 0) {
            while (current.getNext() != null) {
                current = current.getNext();
                if (current.getValue().equals(value)) {
                    doesContain = true;
                    break;
                }
            }
        }
        if (!doesContain) {
            Node<E> node = new Node<>(value);
            tail.setNext(node);
            node.setPrivious(tail);
            tail = node;
            size++;
        }
    }
    /**
     * @return number of elements in the container
     */
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
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
