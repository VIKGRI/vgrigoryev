package ru.grigoryev.game;


/**
*The class implements MoveBehavior interface and represents movement behavior as rook figure has.
*@author vgrigoryev
*@since 17.09.2017
*@version 1
*/
public class MoveAsRook implements MoveBehavior {
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
			throw new ImpossibleMoveException("Rook is not able to move in this destination");
		}
		int wayLength = 0;
		int startIndex = 0;
		int endIndex = 0;
		if (destination.getColumn() == source.getColumn()) {
			if (destination.getRow() > source.getRow()) {
				wayLength = destination.getRow() - source.getRow();
				startIndex = source.getRow() + 1;
				endIndex = destination.getRow() + 1;
			} else {
				wayLength = source.getRow() - destination.getRow();
				startIndex = destination.getRow();
				endIndex = source.getRow();
			}
		} else {
			if (destination.getColumn() > source.getColumn()) {
				wayLength = destination.getColumn() - source.getColumn();
				startIndex = source.getColumn() + 1;
				endIndex = destination.getColumn() + 1;
			} else {
				wayLength = source.getColumn() - destination.getColumn();
				startIndex = destination.getColumn();
				endIndex = source.getColumn();
			}
		}
		Cell[] way = new Cell[wayLength];
		if (destination.getColumn() == source.getColumn()) {
			for (int k = 0; startIndex < endIndex; startIndex++, k++) {
				way[k] = new Cell(destination.getColumn(), startIndex);
			}
		} else {
			for (int k = 0; startIndex < endIndex; startIndex++, k++) {
				way[k] = new Cell(startIndex, destination.getRow());
			}
		}
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
	*@return the array of the possible destinations of Rook depending on source
	*/
	private Cell[] allPossibleMoves(Cell source) {
		Cell[] moves = new Cell[2 * Board.BOARD_SIZE - 2]; // Вертикаль и горизонталь, которые бьет ладья
		int columnIndex = source.getColumn();
		int rowIndex = source.getRow();
		int k = 0;
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			if (i != rowIndex) {
				moves[k++] = new Cell(columnIndex, i);
			}
		}
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			if (i != columnIndex) {
				moves[k++] = new Cell(i, rowIndex);
			}
		}
		return  moves;
	}
}