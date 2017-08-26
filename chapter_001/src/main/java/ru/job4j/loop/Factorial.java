package ru.job4j.loop;

/**
*Class for counting factorial of number.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Factorial {
   /**
     *This method is used for counting factorial of number.
	 *@param n This is the number factorial of which is returned
	 *@return factorial of number
	 */
   public int calc(int n) {
	  if (n < 0) {
	      return -1;
	  }
      int factorial = 1;
	  if (n > 1) {
	     for (int i = 2; i <= n; i++) {
			factorial *= i;
	     }
	  }
      return factorial;
   }
}