package ru.job4j.colltest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
*Class provides convesion from list to array and back.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class ConvertList {
	/**
	*Method is used for converting two-dimensional int array into list of Integers.
	*@param array Specified array to convert
	*@return the list which is build from array
	*/
	public List<Integer> toList(int[][] array) {
		List<Integer> result = new ArrayList<Integer>();
		if (array != null) {
			for (int[] arrayValue: array) {
				for (int value: arrayValue) {
				result.add(value);
				}
			}
		}
		return result;
	}
	/**
	*Method is used for converting list of integers into two-dimensional int array with specified rows.
	*@param list Specified list to convert
	*@param rows Specified rows in the array
	*@return the array which is build from list
	*/
	public int[][] toArray(List<Integer> list, int rows) {
		if (rows == 0) {
			throw new UnsupportedOperationException("It is not available to specify 0 rows");
		}
		int columns = list.size() / rows;
		if (list.size() % rows != 0) {
			columns++;
		}
		int[][] result = new int[rows][columns];
		ListIterator<Integer> it = list.listIterator();
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				if (it.hasNext()) {
					result[i][j] = it.next();
				} else {
					result[i][j] = 0;
				}
			}
		}
		return result;
	}
}