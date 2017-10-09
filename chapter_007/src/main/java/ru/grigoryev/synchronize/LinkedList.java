package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * Implementation of thread safe linked list.
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 09.10.2017
 */
@ThreadSafe
public class LinkedList<E> implements Iterable<E> {
    /**
     * The node in the list represents the head of list.
     */
    @GuardedBy("this")
    private Node<E> head = null;
    /**
     * The last node in the list.
     */
    @GuardedBy("this")
    private Node<E> tail = head;
    /**
     * Number of elements in the list.
     */
    @GuardedBy("this")
    private int size = 0;
    /**
     * Current node in the iterator.
     */
    @GuardedBy("this")
    private Node<E> currentIteratorNode;
    /**
     * Default constructor.
     */
    public LinkedList() {
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
    public synchronized void add(E value) {
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
    public synchronized boolean delete(int position) {
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
    public synchronized E get(int position) {
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
    public synchronized Node<E> getNode(int position) {
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
    public synchronized int size() {
        return this.size;
    }

    /**
     * Checks whether the list has cycle dependencies or not.
     * @return true if the the list has cycle dependencies and false otherwise
     */
    public synchronized boolean hasCycle() {
        Node<E> last = head;
        boolean hasNext = false;
        Node<E> outerCurrent = head;
        Node<E> innerCurrent;
        outer:
        while (outerCurrent.getNext() != null) {
            outerCurrent = outerCurrent.getNext();
            innerCurrent = head;
            while (innerCurrent.getNext() != outerCurrent) {
                innerCurrent = innerCurrent.getNext();
                if (outerCurrent.getNext() == innerCurrent) {
                    hasNext = true;
                    break outer;
                }
            }
        }
        return hasNext;
    }

    /**
     * Returns true if the iteration has more elements.
     * @return true if the iteration has more elements
     */
    private synchronized boolean hasNextValue() {
        return this.currentIteratorNode.getNext() != null;
    }
    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     */
    private synchronized E nextValue() {
        this.currentIteratorNode = this.currentIteratorNode.getNext();
        return this.currentIteratorNode.getValue();
    }
    @Override
    public Iterator<E> iterator() {
        this.currentIteratorNode = head;
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return hasNextValue();
            }

            @Override
            public E next() {
                return nextValue();
            }
        };
    }
}
