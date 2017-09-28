package ru.grigoryev.lists;

import org.junit.Test;


import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing LinkedContainer class's methods.
 *
 * @author vgrigoryev
 * @since 25.09.2017
 */
public class LinkedContainerTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddMoreElementsThenSizeMatches() {
        LinkedContainer<Integer> container = new LinkedContainer<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        int result = container.size();
        int expect = 5;

        assertThat(result, is(expect));
    }
    /**
     * Testing delete() method.
     */
    @Test
    public void whenDeleteElementThenListResizes() {
        LinkedContainer<Integer> container = new LinkedContainer<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        container.delete(4);
        container.delete(3);
        container.delete(2);
        container.delete(1);

        Iterator<Integer> it = container.iterator();
        int[] result = new int[1];
        result[0] = it.next();


        int[] expect = new int[]{1};
        assertThat(result, is(expect));
    }

    /**
     * Testing get() method.
     */
    @Test
    public void whenGetElementByPositionThenValueMatches() {
        LinkedContainer<Integer> container = new LinkedContainer<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        Integer result = container.get(2);
        Integer expect = 3;

        assertThat(result, is(expect));
    }

    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        LinkedContainer<Integer> container = new LinkedContainer<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        Iterator<Integer> it = container.iterator();
        int[] result = new int[2];
        result[0] = it.next();
        result[1] = it.next();

        int[] expect = new int[]{1, 2};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method when tail has reference on the previous element.
     */
    @Test
    public void  whenHasCycleThenTrue() {
        LinkedContainer<Integer> list = new LinkedContainer<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Node<Integer> second = list.getNode(1);
        Node<Integer> last = list.getNode(3);
        last.setNext(second);
        boolean result = list.hasCycle();

        assertThat(result, is(true));
    }
    /**
     * Testing hasNext() method when some element in the middle has reference on the previous element.
     */
    @Test
    public void  whenHasCycleInTheMiddleThenTrue() {
        LinkedContainer<Integer> list = new LinkedContainer<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        Node<Integer> second = list.getNode(1);
        Node<Integer> last = list.getNode(3);
        last.setNext(second);
        boolean result = list.hasCycle();

        assertThat(result, is(true));
    }
    /**
     * Testing hasCycle() method.
     */
    @Test
    public void whenHasNoCycleThenFalse() {
        LinkedContainer<Integer> list = new LinkedContainer<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        boolean result = list.hasCycle();

        assertThat(result, is(false));
    }
}