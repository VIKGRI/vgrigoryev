package ru.grigoryev.lists;

/**
 * Class represents node of list.
 *
 * @param <T> type of element wrappered in the node.
 */
public class Node<T> {
    /**
     * Value which is wrappered in the node.
     */
    private T value;

    /**
     * Refference on the next node.
     */
    private Node<T> next;
    /**
     * Refference on the privious node.
     */
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
    public T getValue() {
        return value;
    }

    /**
     * Returns reference on the next node.
     *
     * @return reference on the next node
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Returns reference on the privious node.
     *
     * @return reference on the privious node
     */
    public Node<T> getPrivious() {
        return privious;
    }

    /**
     * Sets value from the node.
     *
     * @param value specified value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Sets reference on the next node.
     *
     * @param next specified Node which is referenced by the current
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Sets reference on the privious node.
     *
     * @param privious specified Node which references on the current
     */
    public void setPrivious(Node<T> privious) {
        this.privious = privious;
    }
}
