package ru.job4j.shapes;


/**
*Class represent the shape of square.
*@author vgrigoryev
*@since 07.09.2017
*@version 1
*/
public class Square implements Shape {
	/**
	*This method provides constructing of string represantation of square.
	*@return String represantation of square
	*/
	public String pic() {
		StringBuilder picture = new StringBuilder();
		picture.append("+ + + +\n");
		picture.append("+     +\n");
		picture.append("+     +\n");
		picture.append("+ + + +\n");
		return picture.toString();
	}
}