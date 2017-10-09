package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Class represents node of list.
 *
 * @param <T> type of element wrappered in the node.
 */
@ThreadSafe
public class Node<T> {
    /**
     * Value which is wrappered in the node.
     */
    @GuardedBy("this")
    private T value;

    /**
     * Refference on the next node.
     */
    @GuardedBy("this")
    private Node<T> next;
    /**
     * Refference on the privious node.
     */
    @GuardedBy("this")
    private Node<T> privious;

    /**
     * Constructor.
     *
     * @param value specified value
     */
    public Node(T value) {
        this.value = value;
        this.privious = null;
        this.next = null;
    }

    /**
     * Default constructor.
     */
    public Node() {
        this.value = null;
        this.privious = null;
        this.next = null;
    }

    /**
     * Gets value from the node.
     *
     * @return value stored in the node
     */
    public synchronized T getValue() {
        return this.value;
    }

    /**
     * Returns reference on the next node.
     *
     * @return reference on the next node
     */
    public synchronized Node<T> getNext() {
        return this.next;
    }

    /**
     * Returns reference on the privious node.
     *
     * @return reference on the privious node
     */
    public synchronized Node<T> getPrivious() {
        return this.privious;
    }

    /**
     * Sets value from the node.
     *
     * @param value specified value
     */
    public synchronized void setValue(T value) {
        this.value = value;
    }

    /**
     * Sets reference on the next node.
     *
     * @param next specified Node which is referenced by the current
     */
    public synchronized void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Sets reference on the privious node.
     *
     * @param privious specified Node which references on the current
     */
    public synchronized void setPrivious(Node<T> privious) {
        this.privious = privious;
    }
}
