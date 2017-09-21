package ru.grigoryev.iterators;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Class for testing TwoDArrayIterator class's methods.
 * @author vgrigoryev
 * @since 21.09.2017
 */
public class TwoDArrayIteratorTest {
    /**
     *Testing next() method.
     */
    @Test
    public void whenGetNextCallPointerShouldForward() {
        TwoDArrayIterator<Integer> it = new TwoDArrayIterator<>(new Integer[][] {{1, 2, 3}, {4, 5}, {6, 7, 8}});

        int[] result = new int[4];
        result[0] = it.next();
        result[1] = it.next();
        result[2] = it.next();
        result[3] = it.next();

        int[] expect = new int[] {1, 2, 3, 4};
        assertThat(result, is(expect));
    }

    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        TwoDArrayIterator<Integer> it = new TwoDArrayIterator<>(new Integer[][] {{1}});
        it.next();
        it.hasNext();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }
}