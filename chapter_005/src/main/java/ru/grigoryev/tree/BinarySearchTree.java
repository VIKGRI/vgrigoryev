package ru.grigoryev.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *Represents structure of binary search tree.
 * @param <E> type of the elements stored in the tree.
 */
public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
    /**
     * @param <E> type of elements stored in the tree.
     */
    private class Node<E> {
        /**
         * right child.
         */
        private Node<E> right;
        /**
         * left child.
         */
        private Node<E> left;
        /**
         * node's parent.
         */
        private Node<E> parent;
        /**
         * value stored in the node.
         */
        private E value;

        /**
         * Constructor.
         *
         * @param value value stored in this node
         */
        Node(E value) {
            this.right = null;
            this.left = null;
            this.parent = null;
            this.value = value;
        }
    }

    /**
     * Root of the tree.
     */
    private Node<E> root = null;
    /**
     * Search node with specified value in the tree with specified root.
     * @param root specified root
     * @param value specified value to find
     * @return node with specified value or null
     */
    private Node<E> treeSearchFromNode(Node<E> root, E value) {
        if (root == null || value.equals(root.value)) {
            return root;
        }
        if (value.compareTo(root.value) < 0) {
            return this.treeSearchFromNode(root.left, value);
        } else {
            return this.treeSearchFromNode(root.right, value);
        }
    }
    /**
     * Adds new node with specified value in the tree with specified root.
     * @param root specified root
     * @param value Adds new node with specified value to the tree with specified root.
     */
    private void addFromNode(Node<E> root, E value) {
        Node<E> y = null;
        Node<E> x = root;
        while (x != null) {
            y = x;
            if (value.compareTo(x.value) <= 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        Node<E> newNode = new Node<E>(value);
        newNode.parent = y;
        if (y == null) {
            this.root = newNode;
        } else {
            if (value.compareTo(y.value) <= 0) {
                y.left = newNode;
            } else {
                y.right = newNode;
            }
        }
    }

    /**
     * Adds new node with specified value.
     * @param value specified value
     */
    public void add(E value) {
        this.addFromNode(this.root, value);
    }

    /**
     * @param value specified value to find
     * @return node with specified value or null
     */
    public E treeSearch(E value) {
        Node<E> result = treeSearchFromNode(this.root, value);
        return result == null ? null : result.value;
    }

    /**
     * @param root root of the tree or subtree
     * @param list list representation of the tree in the preorder
     */
    private void asList(Node<E> root, List<E> list) {
        if (root != null) {
            asList(root.left, list);
            list.add(root.value);
            asList(root.right, list);
        }
    }

    @Override
    public Iterator<E> iterator() {
        LinkedList<E> listView = new LinkedList<>();
        asList(root, listView);
        return listView.iterator();
    }
}
