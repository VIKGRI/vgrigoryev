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
		game.addFigure(new Bishop(new Cell(2, 0), Color.BLACK));
		game.addFigure(new Bishop(new Cell(5, 0), Color.WHITE));
		game.addFigure(new Knight(new Cell(4, 5), Color.WHITE));
		game.addFigure(new Rook(new Cell(6, 2), Color.BLACK));
		game.addFigure(new Pawn(new Cell(0, 1), Color.BLACK));
		game.addFigure(new Queen(new Cell(0, 5), Color.BLACK));
		game.addFigure(new Queen(new Cell(0, 7), Color.WHITE));
		try {
			System.out.print(game.move(new Cell(2, 0), new Cell(4, 2)) + " ");
			System.out.print(game.move(new Cell(4, 5), new Cell(6, 6)) + " ");
			System.out.print(game.move(new Cell(6, 2), new Cell(6, 5)) + " ");
			System.out.print(game.move(new Cell(0, 1), new Cell(0, 3)) + " ");
			System.out.print(game.move(new Cell(0, 5), new Cell(2, 7)) + " ");
			System.out.println(game.move(new Cell(0, 7), new Cell(1, 7)));
		} catch (ImpossibleMoveException ime) {
			System.out.println(ime.getMessage());
		} catch (OccupiedWayException owe) {
			System.out.println(owe.getMessage());
		} catch (FigureNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		assertThat(out.toString(), is(String.format("true" + " " + "true"
						+ " " + "true" + " " + "true" + " " + "true" + " " + "true" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	*Method for testing movement of the figure Bishop when this movement is not according to the rules.
	 * @throws ImpossibleMoveException if the figure is not able to move this way
	 * @throws OccupiedWayException if the destination cell is occupied or another figure is on the way
	 * @throws FigureNotFoundException if source cell doesn't hold any figure
	*/
	@Test(expected = ImpossibleMoveException.class)
	public void whenMoveFigureNotInRightCellThenImpossibleMoveExceptionOccurs()
			throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0), Color.BLACK));
		game.addFigure(new Bishop(new Cell(5, 0), Color.BLACK));
		System.out.println(game.move(new Cell(2, 0), new Cell(6, 5)));
	}
	/**
	*Method for testing case when the figure in the source cell is not found.
	 * @throws ImpossibleMoveException if the figure is not able to move this way
	 * @throws OccupiedWayException if the destination cell is occupied or another figure is on the way
	 * @throws FigureNotFoundException if source cell doesn't hold any figure
	*/
	@Test(expected = FigureNotFoundException.class)
	public void whenFigureNotFoundThenFigureNotFoundExceptionOccurs()
			throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0), Color.WHITE));
		game.addFigure(new Bishop(new Cell(5, 0), Color.WHITE));
		System.out.println(game.move(new Cell(3, 0), new Cell(6, 5)));
	}
	/**
	*Method for testing movement if there is another figure on the way.
	 * @throws ImpossibleMoveException if the figure is not able to move this way
	 * @throws OccupiedWayException if the destination cell is occupied or another figure is on the way
	 * @throws FigureNotFoundException if source cell doesn't hold any figure
	*/
	@Test(expected = OccupiedWayException.class)
	public void whenAnotherFigureOnWayThenOccupiedWayExceptionOccurs()
			throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0), Color.BLACK));
		game.addFigure(new Bishop(new Cell(5, 3), Color.BLACK));
		System.out.println(game.move(new Cell(2, 0), new Cell(7, 5)));
	}
	/**
	*Method for testing movement if the destination cell is occupied.
		 * @throws ImpossibleMoveException if the figure is not able to move this way
		 * @throws OccupiedWayException if the destination cell is occupied or another figure is on the way
		 * @throws FigureNotFoundException if source cell doesn't hold any figure
	*/
	@Test(expected = OccupiedWayException.class)
	public void whenDestinationCellOccupiedThenOccupiedWayExceptionOccurs()
			throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Board game = new Board();
		game.addFigure(new Bishop(new Cell(2, 0), Color.WHITE));
		game.addFigure(new Bishop(new Cell(7, 5), Color.WHITE));
		System.out.println(game.move(new Cell(2, 0), new Cell(7, 5)));
	}
}