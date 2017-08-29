package ru.job4j.array;

/**
*Class for reversing array.
*@author vgrigoryev
*@since 29.08.2017
*@version 1
*/
public class Turn {
   /**
     *This method is used for reversing an array.
	 *@param array This parameter represents the array to reverse
	 *@return reversed array
	 */
    public int[] back(int[] array) {
		int tmp = 0;
		for (int i = 0, j = array.length - 1; i < array.length / 2; i++, j--) {
		    tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	    return array;
    }
}