package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class RotateArray testing.
*@author vgrigoryev
*@since 01.09.2017
*@version 1
*/
public class RotateArrayTest {
	/**
     *method for rotate method testing.
     */
    @Test
     public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
    	RotateArray bRotate = new RotateArray();
		int[][] array = {
			{1, 5},
			{4, 2}
			};
		int[][] result = bRotate.rotate(array);
		int[][] expectArray = {
			{4, 1},
			{2, 5}
			};
		assertThat(result, is(expectArray));
    }
    /**
     *method for rotate method testing.
     */
    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
    	RotateArray bRotate = new RotateArray();
		int[][] array = {
			{1, 5, 3},
			{4, 2, 7},
			{8, 9, 0}
			};
		int[][] result = bRotate.rotate(array);
		int[][] expectArray = {
			{8, 4, 1},
			{9, 2, 5},
			{0, 7, 3}
			};
		assertThat(result, is(expectArray));
    }
}