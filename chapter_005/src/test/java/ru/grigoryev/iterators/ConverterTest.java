package ru.grigoryev.iterators;

import org.junit.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Converter class's methods.
 * @author vgrigoryev
 * @since 22.09.2017
 */
public class ConverterTest {
    /**
     * Testing next() method.
     */
    @Test
    public void whenItHasTwoInnerIt() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator(),
                Collections.singletonList(5).iterator(),
                Collections.singletonList(11).iterator(),
                Collections.singletonList(33).iterator()
        ).iterator();

        Iterator<Integer> conv = new Converter().convert(it);
        conv.next();
        conv.next();
        int result = conv.next();

        assertThat(result, is(5));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();

        Iterator<Integer> conv = new Converter().convert(it);
        conv.next();
        conv.next();
        conv.hasNext();
        boolean result = conv.hasNext();

        assertThat(result, is(false));
    }
}