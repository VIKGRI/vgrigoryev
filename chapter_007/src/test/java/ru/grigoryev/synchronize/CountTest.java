package ru.grigoryev.synchronize;

import org.junit.Test;
import ru.grigoryev.threads.Value;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Class for testing thread safe increment operation.
 *@author vgrigoryev
 *@since 07.10.2017
 *@version 1
 */

public class CountTest {
    /**
     * Method for testing increment method.
     */
    @Test
    public void whenIncrementsFromFourThreadsThenUpdatesAreNotLost() {
        Value value = new Value(0);
        Count count1 = new Count(value);
        Thread t1 = new Thread(count1);
        Count count2 = new Count(value);
        Thread t2 = new Thread(count1);
        Count count3 = new Count(value);
        Thread t3 = new Thread(count1);
        Count count4 = new Count(value);
        Thread t4 = new Thread(count1);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int result = value.getValue();
        assertThat(result, is(200));
    }
}