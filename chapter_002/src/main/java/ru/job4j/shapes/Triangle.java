package ru.job4j.shapes;


/**
*Class represent the shape of triangle.
*@author vgrigoryev
*@since 07.09.2017
*@version 1
*/
public class Triangle implements Shape {
	/**
	*This method provides constructing of string represantation of triangle.
	*@return String represantation of triangle
	*/
	public String pic() {
		StringBuilder picture = new StringBuilder();
		picture.append("   +   \n");
		picture.append("  +++  \n");
		picture.append(" +++++ \n");
		picture.append("+++++++\n");
		return picture.toString();
	}
}