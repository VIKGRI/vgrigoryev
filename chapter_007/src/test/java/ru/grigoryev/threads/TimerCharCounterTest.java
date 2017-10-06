package ru.grigoryev.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Class for TimerCharCounter class testing.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class TimerCharCounterTest {
    /**
     * Method for testing count method.
     */
    @Test
    public void whenCountCharactersThenAmountMatches() {

        TimerCharCounter counter = new TimerCharCounter();
        int result = counter.count("text2.txt", 10000);

        System.out.println(result);

        assertThat(result, is(128));
    }
}