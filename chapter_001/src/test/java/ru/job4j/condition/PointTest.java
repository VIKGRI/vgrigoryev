package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class testing Point class methods.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class PointTest {
	/**
     *Testing method is.
     */
    @Test
    public void whenPointOnLineThenTrue() {
		//create of new point.
        Point a = new Point(1, 1);
        // execute method - is and get result;
        boolean rsl = a.is(0, 1);
        // assert result by excepted value.
        assertThat(rsl, is(true));
   }
    /**
     *Testing method is.
     */
    @Test
    public void whenPointNotOnLineThenFalse() {
        //create of new point.
        Point a = new Point(1, 1);
        // execute method - is and get result;
        boolean rsl = a.is(1, 1);
        // assert result by excepted value.
        assertThat(rsl, is(false));
    }
}