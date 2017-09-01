package ru.job4j.array;

import java.util.Arrays;

/**
*Class for deleting duplicates from the array.
*@author vgrigoryev
*@since 01.09.2017
*@version 1
*/
public class ArrayDuplicate {
   /**
     *This method is used for deleting duplicates from the array.
	 *@param array This parameter represents the array to delete duplicates from
	 *@return array which contains unique strings
	 */
    public String[] remove(String[] array) {
		int duplicatesCount = 0;
		String tmp = null;
		for (int i = 0; i < array.length - duplicatesCount; i++) {
			String key = array[i];
			for (int j = i + 1; j < array.length - duplicatesCount; j++) {
				if (key.equals(array[j])) {
					tmp = array[j];
					array[j] = array[array.length - 1 - duplicatesCount];
					array[array.length - 1 - duplicatesCount] = tmp;
					j--;
					duplicatesCount++;
				}
			}
		}
	    return Arrays.copyOf(array, array.length - duplicatesCount);
    }
}