package ru.grigoryev.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing SimpleArrayTest class's methods.
 * @author vgrigoryev
 * @since 22.09.2017
 */
public class SimpleArrayTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddNumberThenGetIt() {
        SimpleArray<Integer> array = new SimpleArray<>(3);
        array.add(55);
        int result = array.get(0);
        assertThat(result, is(55));
    }
    /**
     * Testing delete() method.
     */
    @Test
    public void whenDeleteNumberThenGetTrue() {
        SimpleArray<Integer> array = new SimpleArray<>(2);
        array.add(55);
        array.add(33);
        boolean isDeleted = array.delete(0);
        boolean doesExist = array.contains(55);
        assertThat(isDeleted && !doesExist, is(true));
    }
    /**
     * Testing update() method.
     */
    @Test
    public void whenUpdateNumberThenGetTrue() {
        SimpleArray<Integer> array = new SimpleArray<>(2);
        array.add(55);
        array.add(33);
        boolean isDeleted = array.update(0, 77);
        int result = array.get(0);
        assertThat(result, is(77));
    }
    /**
     * Testing get() method.
     */
    @Test
    public void whenGetNumberThenReturnTheOneAdded() {
        SimpleArray<Integer> array = new SimpleArray<>(2);
        array.add(33);
        int result = array.get(0);
        assertThat(result, is(33));
    }
    /**
     * Testing contains() method.
     */
    @Test
    public void whenAddNumberThenReturnContainsReturnTrue() {
        SimpleArray<Integer> array = new SimpleArray<>(2);
        array.add(33);
        boolean result = array.contains(33);
        assertThat(result, is(true));
    }
}