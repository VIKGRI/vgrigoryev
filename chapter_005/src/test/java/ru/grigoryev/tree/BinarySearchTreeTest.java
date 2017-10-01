package ru.grigoryev.tree;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing BinarySearchTree class's methods.
 *
 * @author vgrigoryev
 * @since 01.10.2017
 */
public class BinarySearchTreeTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddElementsThenOnlyNodesWithPresentParentsAndUniqueValuesAdded() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(11);
        tree.add(2);
        tree.add(6);
        tree.add(4);
        tree.add(12);
        tree.add(33);
        tree.add(14);
        tree.add(3);
        tree.add(15);
        tree.add(8);

        for (Integer aTree : tree) {
            System.out.println(aTree);
        }

        assertThat(out.toString(), is(String.format("2" + "%s" + "3" + "%<s" + "4" + "%<s" + "6" + "%<s" + "8" + "%<s"
                        + "11" + "%<s" + "12" + "%<s" + "14" + "%<s" + "15" + "%<s" + "33" + "%<s",
                System.getProperty("line.separator"))));
    }

    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(11);
        tree.add(2);
        tree.add(6);
        tree.add(4);
        tree.add(7);
        tree.add(12);
        tree.add(33);
        tree.add(14);
        tree.add(3);
        tree.add(15);
        tree.add(8);

        Iterator<Integer> it = tree.iterator();
        int[] result = new int[3];
        result[0] = it.next();
        result[1] = it.next();
        result[2] = it.next();

        int[] expect = new int[]{2, 3, 4};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(11);
        tree.add(2);
        tree.add(6);

        Iterator<Integer> it = tree.iterator();
        it.next();
        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenFindPresentValueThenReturnsValue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(11);
        tree.add(2);
        tree.add(6);

        Integer result = tree.treeSearch(11);

        assertThat(result, is(11));
    }
}