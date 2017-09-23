package ru.job4j.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class which has methods to orginize department's
 * hierarchy and manipulate that data.
 *
 * @author vgrigoryev
 * @version 1
 * @since 22.09.2017
 */
public class DepartmentSort {
    /**
     * Method which constructs tree of hierarchy of departments
     * from the array of strings.
     * @param departments array of department names.
     * @return tree of nodes each of which represents a particular department
     */
    public Node buildTree(final String[] departments) {
        if (departments == null) {
            return null;
        }
        //фиктивный узел
        Node head = new Node("Null", 0);
        // уровень иерархии
        int level = 1;
        // список соддержит узлы на данном уровне иерархии
        ArrayList<String> parentIds = new ArrayList<>();
        // parentId.add(n1.getId());
        Node currentParent = head;
        //если новая ветвь создается от фиктивного узла
        boolean newBranch = false;
        //Переменная куда считываются строки после парсера
        String[] row;
        for (int i = 0; i < departments.length; i++) {
            row = departments[i].split("[\\W&&[^0-9]]");
            if (level <= row.length) {
                while (level <= row.length) {
                    if (currentParent.getLevel() != level - 1) {
                        parentIds = this.getParentIds(currentParent.getChilds());
                    }
                    /*Формируем строку с его id подразделения на уровень выше,
                    * чтобы проверить вкючается ли оно в вышестоящее
                    */
                    String checkParent = "";
                    for (int k = 0; k < level - 1; k++) {
                        if (checkParent.length() == 0) {
                            checkParent += row[k];
                        } else {
                            checkParent += "\\" + row[k];
                        }
                    }

                    if ((parentIds.size() != 0 && parentIds.contains(checkParent)) || head.getChilds().size() == 0 || newBranch) {
                        newBranch = false;
                        // Формируем строку с id текущего додразделения.
                        String nodeId = "";
                        for (int j = 0; j < level; j++) {
                            if (nodeId.length() == 0) {
                                nodeId += row[j];
                            } else {
                                nodeId += "\\" + row[j];
                            }
                        }
                    /*Устанавливаем нового текущего родителя*/
                        if (checkParent.length() != 0 && !checkParent.equals(currentParent.getId())) {
                            currentParent = this.getCurrentNode(currentParent.getChilds(), checkParent);
                        }

                        Node n = new Node(nodeId, level);
                    /*
                    Если у текущего родителя еще нет такого дочернего узла, то добавляем
                    */
                    if (!currentParent.getChilds().contains(n)) {
                            currentParent.addChild(n);
                        }

                        if (level == row.length && level != 1) {
                            break;
                        }
                        level++;
                    }
                }
            } else { // Если в очередной строке лексем меньше, чем текущий уровень иерархии,
                     // то начинаем все от фиктивного узла
                level = 1;
                currentParent = head;
                parentIds = new ArrayList<>();
                for (Node n : currentParent.getChilds()) {
                    parentIds.add(n.getId());
                }
                newBranch = true;
                i--;
            }
        }
        return head;
    }

    /**
     * Prints all the nodes recursively.
     * @param head tree of hierarchy which starts from head
     */
    public void print(Node head) {
        if (head.getLevel() != 0) {
            System.out.println(head.getId());
        }
        for (Node g : head.getChilds()) {
            print(g);
        }
    }

    /**
     * Sorts tree in ascending order.
     * @param head tree of hierarchy which starts from head
     */
    public void sortAsc(Node head) {
        for (Node g : head.getChilds()) {
            sortAsc(g);
        }
        Collections.sort(head.getChilds());
    }
    /**
     * Sorts tree in descending order.
     * @param head tree of hierarchy which starts from head
     */
    public void sortDesc(Node head) {
        for (Node g : head.getChilds()) {
            sortDesc(g);
        }
        Collections.sort(head.getChilds(), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
    }

    /**
     * Returns list of id of nodes on current level of hierarchy.
     * @param nodes list of nodes on current level of hierarchy
     * @return list of id of nodes on current level of hierarchy
     */
    private ArrayList<String> getParentIds(ArrayList<Node> nodes) {
        ArrayList<String> ids = new ArrayList<>();
        for (Node n : nodes) {
            ids.add(n.getId());
        }
        return ids;
    }

    /**
     * Finds current node by it's id in the list of nodes and returns it.
     * @param nodes list of nodes
     * @param id node's id
     * @return current node
     */
    private Node getCurrentNode(ArrayList<Node> nodes, String id) {
        Node current = null;
        for (Node c : nodes) {
            if (c.getId().equals(id)) {
                current = c;
                break;
            }
        }
        return current;
    }

    /**
     * Checks wether specified list contains node with specified id.
     * @param nodes specified list of nodes
     * @param id specified id
     * @return true if specified list contains node with specified id and false otherwise
     */
    private boolean containsId(ArrayList<String> nodes, String id) {
        boolean contains = false;
        for (String str : nodes) {
            str.equals(id);
            contains = true;
        }
        return contains;
    }
}
