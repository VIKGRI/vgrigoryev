package ru.grigoryev.lists;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Node class's methods.
 *
 * @author vgrigoryev
 * @since 25.09.2017
 */
public class NodeTest {
    /**
     * Testing hasCycle() method.
     */
    @Test
    public void whenHasCycleThenTrue() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        four.setNext(first);

        boolean result = first.hasCycle(first);

        assertThat(result, is(true));
    }
    /**
     * Testing hasCycle() method.
     */
    @Test
    public void whenHasNoCycleThenFalse() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);

        boolean result = first.hasCycle(first);

        assertThat(result, is(false));
    }
}