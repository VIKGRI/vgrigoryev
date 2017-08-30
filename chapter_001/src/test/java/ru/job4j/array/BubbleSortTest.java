package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class BubbleSort testing.
*@author vgrigoryev
*@since 30.08.2017
*@version 1
*/
public class BubbleSortTest {
	/**
     *method for sort method testing.
     */
    @Test
     public void whenSortArrayWithTenElementsThenSortedArray() {
    	BubbleSort bSort = new BubbleSort();
		int[] array = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
		int[] result = bSort.sort(array);
		int[] expectArray = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
		assertThat(result, is(expectArray));
    }
    /**
     *method for sort method testing.
     */
    @Test
    public void whenSortArrayWithOneElementThenThisElement() {
    	BubbleSort bSort = new BubbleSort();
		int[] array = {1};
		int[] result = bSort.sort(array);
		int[] expectArray = {1};
		assertThat(result, is(expectArray));
    }
}