package ru.job4j.condition;

/**
*Class Point.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Point {
	/**
	*The horizontal distance of the point.
	*/
   private int x;
   /**
	*The vertical distance of the point.
	*/
   private int y;
   /**
	*The constructor of the Point.
	@param x - horizontal distance of the point
	@param y - vertical distance of the point
	*/
   public  Point(int x, int y) {
      this.x = x;
      this.y = y;
  }
  /**
	*Getter of x-coordinate.
	*@return horizontal distance of the point
	*/
  public int getX() {
      return this.x;
  }
  /**
	*Getter of y-coordinate.
	@return vertical distance of the point
	*/
  public int getY() {
     return this.y;
  }
  /**
	*Method to find out whether point belongs to this line or not.
	*@param a - first parameter. x-coordinate of point.
	*@param b - second parameter. y-coordinate of point.
	*@return boolean whether point belongs to this line or no
	*/
  public boolean is(int a, int b) {
    return this.y == this.x * a + b;
	}
}