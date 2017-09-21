package ru.grigoryev.iterators;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing EvenArrayIterator class's methods.
 *
 * @author vgrigoryev
 * @since 21.09.2017
 */
public class EvenArrayIteratorTest {
    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenEvenNumbersReturn() {
        EvenArrayIterator it = new EvenArrayIterator(new int[]{4, 2, 1, 1});

        int[] result = new int[2];
        result[0] = (Integer) it.next();
        result[1] = (Integer) it.next();

        int[] expect = new int[]{4, 2};
        assertThat(result, is(expect));
    }

    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallWhereNoEvensThenOneReturn() {
        EvenArrayIterator it = new EvenArrayIterator(new int[]{5, 3, 7, 13});

        int[] result = new int[1];
        result[0] = (Integer) it.next();

        int[] expect = new int[]{1};
        assertThat(result, is(expect));
    }

    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        EvenArrayIterator it = new EvenArrayIterator(new int[]{4, 2, 1, 1});

        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}