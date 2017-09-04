package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class ArrayDuplicate testing.
*@author vgrigoryev
*@since 01.09.2017
*@version 1
*/
public class ArrayDuplicateTest {
	/**
     *method for remove method testing.
     */
    @Test
     public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
    	ArrayDuplicate arNoDuplicates = new ArrayDuplicate();
		String[] array = {"Привет", "Супер", "Супер", "Привет", "Супер", "Мир", "Мир", "Привет"};
		String[] result = arNoDuplicates.remove(array);
		String[] expectArray = {"Привет", "Супер", "Мир"};
		assertThat(result, is(expectArray));
    }
}