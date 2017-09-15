package ru.job4j.colltest;

import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class ConvertList testing.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class ConvertListTest {
	/**
	*method for toList method testing.
	*/
	@Test
	public void whenConvertArrayThenListsMatch() {
		int[][] argArray = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 4, 3}};
		ConvertList cl = new ConvertList();
		List<Integer> result = cl.toList(argArray);
		List<Integer> expectList = new ArrayList<Integer>();
		expectList.add(1);
		expectList.add(2);
		expectList.add(3);
		expectList.add(4);
		expectList.add(5);
		expectList.add(6);
		expectList.add(7);
		expectList.add(4);
		expectList.add(3);
		assertThat(result, is(expectList));
	 }
	/**
	*method for toArray method testing.
	*/
	@Test
	public void whenConvertListThenArraysMatch() {
		ConvertList cl = new ConvertList();
		List<Integer> sourceList = new ArrayList<Integer>();
		sourceList.add(1);
		sourceList.add(2);
		sourceList.add(3);
		sourceList.add(4);
		sourceList.add(5);
		sourceList.add(6);
		sourceList.add(7);
		int[][] result = cl.toArray(sourceList, 3);
		int[][] expectArray = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
		assertThat(result, is(expectArray));
	}
	/**
	*method for convert method testing.
	*/
	@Test
	public void whenConvertListOfArraysOfIntsThenResultListsMatch() {
		ConvertList convertList = new ConvertList();
		List<int[]> list = new ArrayList<int[]>();
		list.add(new int[]{1, 2});
		list.add(new int[]{3, 4, 5, 6});
		list.add(new int[]{7});
		List<Integer> result = convertList.convert(list);
		List<Integer> expectList = new ArrayList<Integer>();
		expectList.add(1);
		expectList.add(2);
		expectList.add(3);
		expectList.add(4);
		expectList.add(5);
		expectList.add(6);
		expectList.add(7);
		assertThat(result, is(expectList));
	 }
}