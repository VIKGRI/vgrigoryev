package ru.job4j.shapes;


/**
*Class represent the tool for painting shapes.
*@author vgrigoryev
*@since 07.09.2017
*@version 1
*/
public class Paint {
	/**
	*This method provides painting a figure.
	*@param shape Object of class which implements Shape intefacee
	*/
	public void draw(Shape shape) {
		System.out.println(shape.pic());
	}
}