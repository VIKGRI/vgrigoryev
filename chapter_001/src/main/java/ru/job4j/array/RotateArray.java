package ru.job4j.array;

/**
*Class for rotating array.
*@author vgrigoryev
*@since 01.09.2017
*@version 1
*/
public class RotateArray {
   /**
     *This method is used for rotating an array.
	 *@param array This parameter represents the array to rotate
	 *@return rotated array
	 */
    public int[][] rotate(int[][] array) {
		int size = array.length;
		int tmp = 0;
		for (int i = 0; i < (size + 1) / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
				tmp = array[j][i];
				array[j][i] = array[size - 1 - i][j];
				array[size - 1 - i][j] = array[size - 1 - j][size - 1 - i];
				array[size - 1 - j][size - 1 - i] = array[i][size - 1 - j];
				array[i][size - 1 - j] = tmp;
				}
			}
		    return array;
		}
    }
