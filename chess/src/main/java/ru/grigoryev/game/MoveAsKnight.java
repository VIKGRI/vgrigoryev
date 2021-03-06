package ru.grigoryev.game;

import java.util.Arrays;

/**
*The class implements MoveBehavior interface and represents movement behavior as knight figure has.
*@author vgrigoryev
*@since 17.09.2017
*@version 1
*/
public class MoveAsKnight implements MoveBehavior {
	/**
	*This method validates whether it is posible to move the figure in the destination cell or not.
	*@param source the position from where to remove the figure
	*@param destination the position where to place the figure
	*@return The array of Cells which the figure should pass through to reach the destination
	*@throws ImpossibleMoveException if the movement is not available for this type of figure
	*/
	public Cell[] way(Cell source, Cell destination) throws ImpossibleMoveException {
		/*
		*Если фигура так не ходит, выбрасываем исключение.
		*/
		if (!this.validate(source, destination)) {
			throw new ImpossibleMoveException("Knight is not able to move in this destination");
		}
		return new Cell[]{new Cell(destination.getColumn(), destination.getRow())};
	}
	/**
	*This method validates whether it is posible to move the figure in the destination cell or not.
	*@param source the position from where to remove the figure
	*@param destination the position where to place the figure
	*@return true if it is posible to move the figure in the destination cell and false otherwise
	*/
	public boolean validate(Cell source, Cell destination) {
		/*
		*Если ячейки совпадают, сразу возвращаем false.
		*/
		if (source.getColumn() == destination.getColumn() && source.getRow() == destination.getRow()) {
			return false;
		}
		boolean isValid = false;
		Cell[] allMoves = this.allPossibleMoves(source); // получаем массив всех допустимых ходов с этой клетки
		/*
		*Просматриваем если среди них та,
		*на которую надо походить.
		*Если есть совпадения,
		*значит фигура может туда походить.
		*/
		for (Cell value : allMoves) {
			if (value.getColumn() == destination.getColumn() && value.getRow() == destination.getRow()) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
	/**
	*This method return the array of the possible destinations of Knight depending on source.
	*@param source the position from where to remove the figure
	*@return the array of the possible destinations of Knight depending on source
	*/
	private Cell[] allPossibleMoves(Cell source) {
		int position = 0;
		Cell[] moves = new Cell[8]; // Максимальное возможное количество ходов для коня из одной клетки
		int columnIndex = source.getColumn();
		int rowIndex = source.getRow();
		if (columnIndex + 1 >= 0 && columnIndex + 1 < Board.BOARD_SIZE && rowIndex + 2 >= 0 && rowIndex + 2 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex + 1, rowIndex + 2);
		}
		if (columnIndex + 2 >= 0 && columnIndex + 2 < Board.BOARD_SIZE && rowIndex + 1 >= 0 && rowIndex + 1 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex + 2, rowIndex + 1);
		}
		if (columnIndex + 2 >= 0 && columnIndex + 2 < Board.BOARD_SIZE && rowIndex - 1 >= 0 && rowIndex - 1 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex + 2, rowIndex - 1);
		}
		if (columnIndex + 1 >= 0 && columnIndex + 1 < Board.BOARD_SIZE && rowIndex - 2 >= 0 && rowIndex - 2 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex + 1, rowIndex - 2);
		}
				if (columnIndex - 1 >= 0 && columnIndex - 1 < Board.BOARD_SIZE && rowIndex - 2 >= 0 && rowIndex - 2 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex - 1, rowIndex - 2);
		}
		if (columnIndex - 2 >= 0 && columnIndex - 2 < Board.BOARD_SIZE && rowIndex - 1 >= 0 && rowIndex - 1 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex - 2, rowIndex - 1);
		}
		if (columnIndex - 2 >= 0 && columnIndex - 2 < Board.BOARD_SIZE && rowIndex + 1 >= 0 && rowIndex + 1 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex - 2, rowIndex + 1);
		}
		if (columnIndex - 1 >= 0 && columnIndex - 1 < Board.BOARD_SIZE && rowIndex + 2 >= 0 && rowIndex + 2 < Board.BOARD_SIZE) {
			moves[position++] = new Cell(columnIndex - 1, rowIndex + 2);
		}
		return Arrays.copyOf(moves, position);
	}
}