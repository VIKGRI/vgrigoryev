package ru.grigoryev.lists;

import org.junit.Test;


import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing DynamicContainer class's methods.
 *
 * @author vgrigoryev
 * @since 25.09.2017
 */
public class DynamicContainerTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddMoreElementsThenCapacityIncreases() {
        DynamicContainer<Integer> container = new DynamicContainer<>(4);
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
     * Testing add() method.
     */
    @Test
    public void whenGetElementByPositionThenValueMatches() {
        DynamicContainer<Integer> container = new DynamicContainer<>(4);
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
        DynamicContainer<Integer> container = new DynamicContainer<>(4);
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
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        DynamicContainer<Integer> container = new DynamicContainer<>(4);
        container.add(1);
        container.add(2);

        Iterator<Integer> it = container.iterator();
        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}