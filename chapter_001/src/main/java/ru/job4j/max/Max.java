package ru.job4j.max;
/**
*Class for evaluating maximum.
*@author vgrigoryev
*@since 25.08.2017
*@version 1
*/
class Max {
   /**
     *This method is used to calculate the maximum of two values.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 *@return maximum value
	 */
    public int max(int first, int second) {
    int max = first >= second ? first : second;
	return max;
	}
}