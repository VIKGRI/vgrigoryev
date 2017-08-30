package ru.job4j.array;

/**
*Class for sorting array.
*@author vgrigoryev
*@since 30.08.2017
*@version 1
*/
public class BubbleSort {
   /**
     *This method is used for sorting an array.
	 *@param array This parameter represents the array to sort
	 *@return sorted array
	 */
    public int[] sort(int[] array) {
		int tmp = 0;
		for (int i = 0; i < array.length - 1; i++) {
            for (int j = array.length - 1; j > i; j--) {
				if (array[j] < array[j - 1]) {
				    tmp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = tmp;
				}
			}
		}
	    return array;
    }
}