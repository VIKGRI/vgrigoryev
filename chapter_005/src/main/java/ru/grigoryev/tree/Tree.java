package ru.grigoryev.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents tree structure.
 *
 * @param <E> type of elements stored in the tree.
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * @param <E> type of elements stored in the tree.
     */
    class Node<E> {
        /**
         * list of children.
         */
        private List<Node<E>> childen;
        /**
         * value stored in the node.
         */
        private E value;

        /**
         * Constructor.
         *
         * @param childen list of childrens of this node
         * @param value   value stored in this node
         */
        Node(List<Node<E>> childen, E value) {
            this.childen = childen;
            this.value = value;
        }
    }

    /**
     * Root of the tree.
     */
    private Node<E> root = null;

    @Override
    public boolean add(E parent, E child) {
        if (child == null) {
            return false;
        }
        if (this.root == null) {
            if (parent == null) {
                this.root = new Node<>(null, child);
                return true;
            } else {
                return false;
            }
        }
        Node<E> searchParent = this.getNode(this.root, parent);
        if (searchParent == null) {
            return false;
        }
        Node<E> searchChild = this.getNode(this.root, child);
        if (searchChild != null) {
            return false;
        }
        if (searchParent.childen == null) {
            searchParent.childen = new LinkedList<>();
        }
        Node<E> newNode = new Node<>(null, child);
        searchParent.childen.add(newNode);
        return true;
    }

    /**
     * @param root root of the tree or subtree
     * @param value value to find
     * @return node which store the same value or null if it doesn't exist
     */
    private Node<E> getNode(Node<E> root, E value) {
        Node<E> result = null;
        if (root.value.equals(value)) {
            return root;
        } else {
            if (root.childen != null) {
                for (Node<E> node : root.childen) {
                    result = getNode(node, value);
                    if (result != null) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param root root of the tree or subtree
     * @param list list representation of the tree in the preorder
     */
    private void asList(Node<E> root, List<E> list) {
        list.add(root.value);
        if (root.childen != null) {
            for (Node<E> node : root.childen) {
                asList(node, list);
            }
        }
    }

    /**
     * @return returns value stored in the root
     */
    public E getRoot() {
        return this.root == null ? null : root.value;
    }

    /**
     * Identifies whether the tree is binary or not.
     * @param root root of the tree
     * @return true if the tree is binary
     */
    private boolean isBinarySubtree(Node<E> root) {
        boolean isBin =  true;
        if (root.childen != null) {
            isBin = root.childen.size() <= 2;
            if (isBin) {
                for (Node<E> node : root.childen) {
                    isBin = this.isBinarySubtree(node);
                    if (!isBin) {
                        break;
                    }
                }
            }
        }
        return  isBin;
    }

    /**
     * Identifies whether the tree is binary or not.
     * @return true if the tree is binary
     */
    public boolean isBinary() {
        if (this.root == null) {
            return true;
        }
        return  isBinarySubtree(this.root);
    }

    @Override
    public Iterator<E> iterator() {
        LinkedList<E> listView = new LinkedList<>();
        asList(root, listView);
        return listView.iterator();
    }
}
