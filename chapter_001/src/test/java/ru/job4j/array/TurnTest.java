package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Turn testing.
*@author vgrigoryev
*@since 29.08.2017
*@version 1
*/
public class TurnTest {
	/**
     *method for back method testing.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
    	Turn turn = new Turn();
		int[] array = {4, 1, 6, 2};
		int[] result = turn.back(array);
		int[] expectArray = {2, 6, 1, 4};
		assertThat(result, is(expectArray));
    }
    /**
     *method for back method testing.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
		Turn turn = new Turn();
		int[] array = {1, 2, 3, 4, 5};
		int[] result = turn.back(array);
		int[] expectArray = {5, 4, 3, 2, 1};
		assertThat(result, is(expectArray));
    }
}