package ru.job4j.max;
/**
*Class for evaluating maximum.
*@author vgrigoryev
*@since 26.08.2017
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
	/**
     *This method is used to calculate the maximum of three values.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 *@param third This is the third parametr
	 *@return maximum value
	 */
    public int max(int first, int second, int third) {
       int max = max(max(first, second), third);
	   return max;
	}
}