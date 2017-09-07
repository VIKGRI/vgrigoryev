package ru.job4j.shapes;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Paint testing.
*@author vgrigoryev
*@since 07.09.2017
*@version 1
*/
public class PaintTest {
	/**
	*method for draw method testing when pass Triangle object.
	*/
	@Test
	public void whenPassTriangleThenReturnTriangle() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Paint paint = new Paint();
		paint.draw(new Triangle());
		StringBuilder expect = new StringBuilder();
		expect.append("   +   \n");
		expect.append("  +++  \n");
		expect.append(" +++++ \n");
		expect.append("+++++++\n");
		assertThat(out.toString(), is(String.format(expect.toString() + "%s", System.getProperty("line.separator"))));
	}
	/**
	*method for draw method testing when pass Square object.
	*/
	@Test
	public void whenPassSquareThenReturnSquare() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Paint paint = new Paint();
		paint.draw(new Square());
		StringBuilder expect = new StringBuilder();
		expect.append("+ + + +\n");
		expect.append("+     +\n");
		expect.append("+     +\n");
		expect.append("+ + + +\n");
		assertThat(out.toString(), is(String.format(expect.toString() + "%s", System.getProperty("line.separator"))));
	}
}