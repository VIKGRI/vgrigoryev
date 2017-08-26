package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Max testing.
*@author vgrigoryev
*@since 25.08.2017
*@version 1
*/
public class MaxTest {
   /**
   *method for max method testing.
   *@author vgrigoryev
   *@since 25.08.2017
   *@version 1
   */
   @Test
   public void whenFirstLessSecond() {
       Max maxim = new Max();
       int result = maxim.max(1, 2);
       assertThat(result, is(2));
   }

    /**
     *method for max method testing.
     *@author vgrigoryev
     *@since 25.08.2017
     *@version 1
     */
    @Test
    public void whenFirstMoreSecond() {
        Max maxim = new Max();
        int result = maxim.max(2, 1);
        assertThat(result, is(2));
    }

    /**
     *method for max method testing.
     *@author vgrigoryev
     *@since 25.08.2017
     *@version 1
     */
    @Test
    public void whenFirstEqualSecond() {
        Max maxim = new Max();
        int result = maxim.max(1, 1);
        assertThat(result, is(1));
    }
	/**
     *method for max method testing.
     *@author vgrigoryev
     *@since 26.08.2017
     *@version 1
     */
    @Test
    public void whenThirdMoreOtherTwo() {
        Max maxim = new Max();
        int result = maxim.max(1, 1, 3);
        assertThat(result, is(3));
    }
}