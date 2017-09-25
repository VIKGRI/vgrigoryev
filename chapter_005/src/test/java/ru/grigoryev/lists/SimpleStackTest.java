package ru.grigoryev.lists;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing SimpleStack class's methods.
 *
 * @author vgrigoryev
 * @since 25.09.2017
 */
public class SimpleStackTest {
    /**
     * Testing push() method.
     */
    @Test
    public void whenPushElementsThenSizeMatches() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        int result = stack.size();
        int expect = 5;

        assertThat(result, is(expect));
    }

    /**
     * Testing poll() method.
     */
    @Test
    public void whenPollElementsThenValuesMatchesAndStackEmpty() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        int[] result = new int[6];
        result[0] = stack.poll();
        result[1] = stack.poll();
        result[2] = stack.poll();
        result[3] = stack.poll();
        result[4] = stack.poll();
        result[5] = stack.size();


        int[] expect = {5, 4, 3, 2, 1, 0};

        assertThat(result, is(expect));
    }
}