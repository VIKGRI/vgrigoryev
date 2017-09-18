package ru.grigoryev.game;

import java.util.Arrays;


/**
*The class implements MoveBehavior interface and represents movement behavior as pawn figure has.
*@author vgrigoryev
*@since 18.09.2017
*@version 1
*/
public class MoveAsPawn implements MoveBehavior {
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
			throw new ImpossibleMoveException("Pawn is not able to move in this destination");
		}
		int wayLength = destination.getRow() - source.getRow();
		Cell[] way = Arrays.copyOf(this.allPossibleMoves(source), wayLength);
		return way;
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
	*This method return the array of the possible destinations of this figure depending on source.
	*@param source the position from where to remove the figure
	*@return the array of the possible destinations of Pawn depending on source
	*/
	private Cell[] allPossibleMoves(Cell source) {
		int wayLength = 0;
		int columnIndex = source.getColumn();
		int rowIndex = source.getRow();
		if (rowIndex == 1 && rowIndex < Board.BOARD_SIZE - 2) {
			wayLength = 2;
		} else {
			if (rowIndex != Board.BOARD_SIZE - 1) {
				wayLength = 1;
			}
		}
		Cell[] moves = new Cell[wayLength];
		for (int i = 0; i < wayLength; i++) {
			moves[i] = new Cell(columnIndex, rowIndex + 1 + i);
		}
		return  moves;
	}
}