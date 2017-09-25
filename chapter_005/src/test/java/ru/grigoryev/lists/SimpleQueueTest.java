package ru.grigoryev.lists;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing SimpleQueue class's methods.
 *
 * @author vgrigoryev
 * @since 25.09.2017
 */
public class SimpleQueueTest {
    /**
     * Testing push() method.
     */
    @Test
    public void whenPushElementsThenSizeMatches() {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);

        int result = queue.size();
        int expect = 5;

        assertThat(result, is(expect));
    }

    /**
     * Testing poll() method.
     */
    @Test
    public void whenPollElementsThenValuesMatchesAndQueueEmpty() {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);

        int[] result = new int[6];
        result[0] = queue.poll();
        result[1] = queue.poll();
        result[2] = queue.poll();
        result[3] = queue.poll();
        result[4] = queue.poll();
        result[5] = queue.size();


        int[] expect = {1, 2, 3, 4, 5, 0};

        assertThat(result, is(expect));
    }
}