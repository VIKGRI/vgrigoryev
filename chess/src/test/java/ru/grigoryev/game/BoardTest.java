package ru.grigoryev.game;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
*Class for Board class testing.
*@author vgrigoryev
*@since 12.09.2017
*@version 1
*/
public class BoardTest {
	/**
	*Method for testing movement of the figure Bishop when it's available.
	*It also doesn't have any figure on the way.
	*/
	@Test
	public void whenMoveFigureOnFreeCellThenReturnTrue() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0)));
		game.addFigure(new Bishop(new Cell(5, 0)));
		try {
			System.out.println(game.move(new Cell(2, 0), new Cell(4, 2)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("true" + "%s", System.getProperty("line.separator"))));
	}
	/**
	*Method for testing movement of the figure Bishop when this movement is not according to the rules.
	*/
	@Test
	public void whenMoveFigureNotInRightCellThenImpossibleMoveExceptionOccurs() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0)));
		game.addFigure(new Bishop(new Cell(5, 0)));
		try {
			System.out.println(game.move(new Cell(2, 0), new Cell(6, 5)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("Bishop is not able to move in this destination" + "%s", System.getProperty("line.separator"))));
	}
	/**
	*Method for testing case when the figure in the source cell is not found.
	*/
	@Test
	public void whenFigureNotFoundThenFigureNotFoundExceptionOccurs() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0)));
		game.addFigure(new Bishop(new Cell(5, 0)));
		try {
			System.out.println(game.move(new Cell(3, 0), new Cell(6, 5)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("Figure is not found" + "%s", System.getProperty("line.separator"))));
	}
	/**
	*Method for testing movement if there is another figure on the way.
	*/
	@Test
	public void whenAnotherFigureOnWayThenOccupiedWayExceptionOccurs() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0)));
		game.addFigure(new Bishop(new Cell(5, 3)));
		try {
			System.out.println(game.move(new Cell(2, 0), new Cell(7, 5)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("Other figure is on your way." + "%s", System.getProperty("line.separator"))));
	}
		/**
	*Method for testing movement if the destination cell is occupied.
	*/
	@Test
	public void whenDestinationCellOccupiedThenOccupiedWayExceptionOccurs() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0)));
		game.addFigure(new Bishop(new Cell(7, 5)));
		try {
			System.out.println(game.move(new Cell(2, 0), new Cell(7, 5)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("Destination cell is occupied" + "%s", System.getProperty("line.separator"))));
	}
}