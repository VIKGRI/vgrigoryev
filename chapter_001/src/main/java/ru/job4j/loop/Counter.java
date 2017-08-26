package ru.job4j.loop;

/**
*Class for counting sum of even numbers in the range.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Counter {
   /**
     *This method is used for counting sum of even numbers in the range.
	 *@param start This is the first number in the range
	 *@param finish This is the last number in the range
	 *@return sum of even numbers
	 */
   public int add(int start, int finish) {
      int sum = 0;
	  if (start <= finish) {
	     for (int i = start; i <= finish; i++) {
	        if (i % 2 == 0) {
		       sum += i;
		    }
	     }
	  }
      return sum;
   }
}