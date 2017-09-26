package ru.grigoryev.sets;

import org.junit.Test;


import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing LinkedSimpleSet class's methods.
 *
 * @author vgrigoryev
 * @since 26.09.2017
 */
public class LinkedSimpleSetTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddMoreElementsThenCapacityIncreasesAndNoDuplicates() {
        LinkedSimpleSet<Integer> set = new LinkedSimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(5);
        set.add(4);
        set.add(4);
        set.add(3);

        int result = set.size();
        int expect = 5;

        assertThat(result, is(expect));
    }

    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        LinkedSimpleSet<Integer> set = new LinkedSimpleSet<>();
        set.add(1);
        set.add(1);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(5);
        set.add(4);
        set.add(4);
        set.add(3);

        Iterator<Integer> it = set.iterator();
        int[] result = new int[2];
        result[0] = it.next();
        result[1] = it.next();

        int[] expect = new int[]{1, 3};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        LinkedSimpleSet<Integer> set = new LinkedSimpleSet<>();
        set.add(2);
        set.add(2);

        Iterator<Integer> it = set.iterator();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }

}