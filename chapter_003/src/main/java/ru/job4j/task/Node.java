package ru.job4j.task;

import java.util.ArrayList;

/**
 * Class represent node which contains information about department
 * and about its sub-departments.
 *
 * @author vgrigoryev
 * @version 1
 * @since 22.09.2017
 */
public class Node implements Comparable<Node> {
    /**
     * It's id of node which corresponds to department's name.
     */
    private String id;
    /**
     * Sub-departments of this department. These are children of this node.
     */
    private ArrayList<Node> childs = new ArrayList<>();
    /**
     * Level of the hierarchy in the tree of departments.
     */
    private int level;

    /**
     * Level's getter.
     * @return level of the node
     */
    public int getLevel() {
        return level;
    }

    /**
     * Level's setter.
     * @param level level of the node
     */
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;

        if (level != node.level) {
            return false;
        }
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + level;
        return result;
    }

    /**
     * Id's getter.
     * @return node's id - name of department
     */
    public String getId() {
        return id;
    }

    /**
     * Allows to get all the childs of this node.
     * @return array of child departments
     */
    public  ArrayList<Node> getChilds() {
        return childs;
    }

    /**
     * Constructor.
     * @param id node's id - name of department
     * @param level level of the node in the department's tree
     */
    public Node(String id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * Id's setter.
     * @param id node's id - name of department
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Adds childs (sub-departments) to the node.
     * @param child child node to add
     */
    public void addChild(Node child) {
        this.childs.add(child);
    }

    @Override
    public int compareTo(Node o) {
        return this.id.compareTo(o.id);
    }
}
