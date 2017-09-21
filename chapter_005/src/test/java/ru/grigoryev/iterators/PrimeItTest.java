package ru.grigoryev.iterators;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing PrimeIt class's methods.
 * @author vgrigoryev
 * @since 21.09.2017
 */
public class PrimeItTest {
    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenPrimeNumbersReturn() {
        PrimeIt it = new PrimeIt(new int[]{3, 4, 5, 6, 7});

        int[] result = new int[3];
        result[0] = (Integer) it.next();
        result[1] = (Integer) it.next();
        result[2] = (Integer) it.next();

        int[] expect = new int[]{3, 5, 7};
        assertThat(result, is(expect));
    }
    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallWhereNoPrimesThenZeroReturn() {
        PrimeIt it = new PrimeIt(new int[]{4, 6, 8, 22});

        int[] result = new int[1];
        result[0] = (Integer) it.next();

        int[] expect = new int[]{0};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        PrimeIt it = new PrimeIt(new int[]{3, 5, 1, 8});

        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}