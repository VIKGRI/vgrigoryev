package ru.job4j.calculator;

/**
*Class Calculator is used to calculate two values.
*Four main operations are performed.
*@author vgrigoryev
*@since 25.08.2017
*@version 1
*/
public class Calculator {
   /**
     *Поле содержит значение результата.
	 */
   private double result;
   /**
     *This method is used to add two doubles.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 */
   public void add(double first, double second) {
        this.result = first + second;
    }
	 /**
     *This method is used to subtract two doubles.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 */
	public void subtract(double first, double second) {
        this.result = first - second;
    }

	 /**
     *This method is used to divide two doubles.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 */
     public void div(double first, double second) {
         if (second != 0) {
             this.result = first / second;
         } else {
             this.result = 0;
         }
     }

	 /**
     *This method is used to multiply two doubles.
	 *@param first This is the first parametr
	 *@param second This is the second parametr
	 */
	public void multiply(double first, double second) {
        this.result = first * second;
	}

	 /**
     *This method is used to get value of result.
	 *@return double result
	 */
    public double getResult() {
        return this.result;
    }
}