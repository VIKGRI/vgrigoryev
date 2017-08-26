package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Factorial testing.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class FactorialTest {
	/**
     *method for calc method testing.
     */
    @Test
    public void whenCalculateFactorialForFiveThenOneHundreedTwenty() {
        Factorial fact = new Factorial();
      	int result = fact.calc(5);
        assertThat(result, is(120));
    }
	/**
     *method for calc method testing.
     */
    @Test
    public void whenCalculateFactorialForZeroThenOne() {
        Factorial fact = new Factorial();
      	int result = fact.calc(0);
        assertThat(result, is(1));
    }
    /**
     *method for calc method testing.
     */
	@Test
    public void whenCalculateFactorialForNegativeThenMinusOne() {
        Factorial fact = new Factorial();
      	int result = fact.calc(-1);
        assertThat(result, is(-1));
    }
}