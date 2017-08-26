package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Counter testing.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class CounterTest {
	/**
     *method for add method testing.
     */
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        Counter counter = new Counter();
      	int evenSum = counter.add(1, 10);
        assertThat(evenSum, is(30));
    }
	/**
     *method for add method testing.
     */
    @Test
    public void whenStartMoreFinishThenZero() {
        Counter counter = new Counter();
      	int evenSum = counter.add(2, 1);
        assertThat(evenSum, is(0));
    }
}