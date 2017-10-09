package ru.grigoryev.synchronize;

import org.junit.Test;


import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing DynamicList class's methods.
 *
 * @author vgrigoryev
 * @since 09.10.2017
 */
public class DynamicListTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddMoreElementsThenCapacityIncreases() {
        DynamicList<Integer> container = new DynamicList<>(4);
        synchronized (this) {
            for (int i = 1; i <= 10; i++) {
                Runnable add = new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 5; j++) {
                           container.add(j);
                        }
                    }
                };
                Thread t = new Thread(add, "Thread #" + i);
                t.start();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int result = container.size();
        int expect = 50;

        assertThat(result, is(expect));
    }
    /**
     * Testing get() method.
     */
    @Test
    public void whenGetElementByPositionThenValueMatches() {
        DynamicList<Integer> container = new DynamicList<>(4);
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
        DynamicList<Integer> container = new DynamicList<>(4);
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
        DynamicList<Integer> container = new DynamicList<>(4);
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