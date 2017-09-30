package ru.grigoryev.tree;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Tree class's methods.
 *
 * @author vgrigoryev
 * @since 30.09.2017
 */
public class TreeTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddElementsThenOnlyNodesWithPresentParentsAndUniqueValuesAdded() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Tree<Integer> tree = new Tree<>();

        tree.add(tree.getRoot(), 1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(2, 6);
        tree.add(3, 7);
        tree.add(7, 8);
        tree.add(7, 9);
        tree.add(20, 1);
        tree.add(3, 6);

        for (Integer aTree : tree) {
            System.out.println(aTree);
        }

        assertThat(out.toString(), is(String.format("1" + "%s" + "2" + "%<s" + "4" + "%<s" + "5" + "%<s" + "6" + "%<s"
                + "3" + "%<s" + "7" + "%<s" + "8" + "%<s" + "9" + "%<s",
                System.getProperty("line.separator"))));
    }
    /**
     * Testing isBinary() method.
     */
    @Test
    public void whenNotBinaryThenFalse() {
        Tree<Integer> tree = new Tree<>();

        tree.add(tree.getRoot(), 1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(2, 6);
        tree.add(3, 7);
        tree.add(7, 8);
        tree.add(7, 9);
        tree.add(20, 1);
        tree.add(3, 6);

        boolean result = tree.isBinary();

        assertThat(result, is(false));
    }
    /**
     * Testing isBinary() method.
     */
    @Test
    public void whenBinaryThenTrue() {
        Tree<Integer> tree = new Tree<>();

        tree.add(tree.getRoot(), 1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(3, 7);
        tree.add(7, 8);
        tree.add(7, 9);
        boolean result = tree.isBinary();

        assertThat(result, is(true));
    }
    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        Tree<Integer> tree = new Tree<>();

        tree.add(tree.getRoot(), 1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(2, 6);
        tree.add(3, 7);
        tree.add(7, 8);
        tree.add(7, 9);
        tree.add(20, 1);
        tree.add(3, 6);

        Iterator<Integer> it = tree.iterator();
        int[] result = new int[3];
        result[0] = it.next();
        result[1] = it.next();
        result[2] = it.next();

        int[] expect = new int[]{1, 2, 4};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        Tree<Integer> tree = new Tree<>();
        tree.add(tree.getRoot(), 1);
        tree.add(1, 2);
        tree.add(1, 3);

        Iterator<Integer> it = tree.iterator();
        it.next();
        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}