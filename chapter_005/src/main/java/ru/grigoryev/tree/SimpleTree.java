package ru.grigoryev.tree;

/**
 * Represents tree structure with add method.
 * @param <E> type of element stored in the tree.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return true if operation succeds
     */
    boolean add(E parent, E child);
}
