package ru.job4j.condition;
/**
*Class Triangle.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Triangle {
   /**
   *The point vertex of triangle.
   */
   private Point a;
   /**
   *The point vertex of triangle.
   */
   private Point b;
   /**
   *The point vertex of triangle.
   */
   private Point c;
   /**
   *The constructor of the Triangle.
   @param a - first point of the triangle
   @param b - second point of the triangle
   @param c - third point of the triangle
   */
   public Triangle(Point a, Point b, Point c) {
       this.a = a;
       this.b = b;
       this.c = c;
    }

    /**
   * @param left Left point.
   * @param right Right point.
   * @return расстояние между точками left и right.
   */
   public double distance(Point left, Point right) {
       return Math.sqrt(Math.pow(left.getX() - right.getX(), 2)
               + Math.pow(left.getY() - right.getY(), 2));
   }

   /**
   * Метод вычисления полупериметра по длинам сторон.
   * @param ab расстояние между точками a b
   * @param ac расстояние между точками a c
   * @param bc расстояние между точками b c
   * @return Периметр.
   */
   public double period(double ab, double ac, double bc) {
       return (ab + ac + bc) / 2;
   }

   /**
   * Method for calculating the area of triangle.
   * @return Вернуть прощадь, если треугольник существует или -1.
   */
   public double area() {
       double rsl = -1;
       double ab = this.distance(this.a, this.b);
       double ac = this.distance(this.a, this.c);
       double bc = this.distance(this.b, this.c);
       double p = this.period(ab, ac, bc);
       if (this.exist(ab, ac, bc)) {
           rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
	   }
       return rsl;
   }

   /**
   * Метод проверяет можно ли построить треугольник с такими длинами сторон.
   * @param ab Длина от точки a b.
    *@param ac Длина от точки a c.
   * @param bc Длина от точки b c.
   * @return boolean whether the triangle exists or not
    */
   private boolean exist(double ab, double ac, double bc) {
      return ab < ac + bc && ac < ab + bc && bc < ab + ac;
   }
}