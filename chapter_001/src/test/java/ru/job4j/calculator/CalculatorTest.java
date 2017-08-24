package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for Calculator testing.
*@author vgrigoryev
*@since 25.08.2017
*@version 1
*/
public class CalculatorTest {
    /**
     *Test add method.
     */
	@Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    /**
     *Test subtract method.
     */
	@Test
    public void whenSubtsactOneFromTwoThenOne() {
        Calculator calc = new Calculator();
        calc.subtract(2D, 1D);
        double result = calc.getResult();
        double expected = 1D;
        assertThat(result, is(expected));
    }

    /**
     *Test divide method.
     */
	@Test
    public void whenDivFourByTwoThenTwo() {
        Calculator calc = new Calculator();
        calc.div(4D, 2D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    /**
     *Test divide method by zero.
     */
	@Test
    public void whenDivFourByZeroThenZero() {
        Calculator calc = new Calculator();
        calc.div(4D, 0D);
        double result = calc.getResult();
        double expected = 0D;
        assertThat(result, is(expected));
    }

    /**
     *Test multiply method by zero.
     */
	@Test
    public void whenMultiplyTwoByTwoThenFour() {
        Calculator calc = new Calculator();
        calc.multiply(2D, 2D);
        double result = calc.getResult();
        double expected = 4D;
        assertThat(result, is(expected));
    }
}