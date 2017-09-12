package ru.grigoryev.game;

/**
*The class implements MoveBehavior interface and represents movement behavior as bishop figure has.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public class MoveAsBishop implements MoveBehavior {
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
			throw new ImpossibleMoveException("Bishop is not able to move in this destination");
		}
		int wayLength = 0;
		if (destination.getColumn() > source.getColumn()) {
			wayLength = destination.getColumn() - source.getColumn();
		} else {
			wayLength = source.getColumn() - destination.getColumn();
		}
		Cell[] way = new Cell[wayLength];
		if (destination.getColumn() > source.getColumn() && destination.getRow() > source.getRow()) {
			for (int i = source.getColumn() + 1, j = source.getRow() + 1, k = 0; k < wayLength; i++, j++, k++) {
				way[k] = new Cell(i, j);
			}
		} else if (destination.getColumn() > source.getColumn() && destination.getRow() < source.getRow()) {
			for (int i = source.getColumn() + 1, j = source.getRow() + 1, k = 0; k < wayLength; i++, j--, k++) {
				way[k] = new Cell(i, j);
			}
		} else if (destination.getColumn() < source.getColumn() && destination.getRow() < source.getRow()) {
			for (int i = source.getColumn() + 1, j = source.getRow() + 1, k = 0; k < wayLength; i--, j--, k++) {
				way[k] = new Cell(i, j);
			}
		} else {
			for (int i = source.getColumn() + 1, j = source.getRow() + 1, k = 0; k < wayLength; i--, j++, k++) {
				way[k] = new Cell(i, j);
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
		boolean isValid = false;
		int columnIndex = 0; // вспомогательный индекс, который представляет номер столбца
		int rowIndex = 0; // вспомогательный индекс, который представляет номер строки
		/*
		*Если ячейки совпадают, сразу возвращаем false.
		*/
		if (source.getColumn() == destination.getColumn() && source.getRow() == destination.getRow()) {
			return false;
		}
		/*
		*Просматриваем все клетки по возрастающей диагонали,
		*на которой расположена фигура слон, и сравниваем с
		*клеткой конечного назначения. Если есть совпадения,
		*значит фигура может туда походить.
		*/
		if (source.getColumn() > source.getRow()) {
			columnIndex = source.getColumn() - source.getRow();
			rowIndex = 0;
			for (; columnIndex < Board.BOARD_SIZE; columnIndex++, rowIndex++) {
				if (columnIndex == destination.getColumn() && rowIndex == destination.getRow()) {
					isValid = true;
				}
			}
		} else {
			columnIndex = 0;
			rowIndex = source.getRow() - source.getColumn();
			for (; rowIndex < Board.BOARD_SIZE; columnIndex++, rowIndex++) {
				if (columnIndex == destination.getColumn() && rowIndex == destination.getRow()) {
					isValid = true;
				}
			}
		}
		/*
		*Просматриваем все клетки по убывающей диагонали (если не нашли в главной),
		*на которой расположена фигура слон, и сравниваем с
		*клеткой конечного назначения. Если есть совпадения,
		*значит фигура может туда походить.
		*/
		if (!isValid) {
			columnIndex = source.getRow() + source.getColumn() + Board.BOARD_SIZE - 1;
			rowIndex = (source.getRow() + source.getColumn()) % (Board.BOARD_SIZE - 1);
			for (; rowIndex >= 0 && columnIndex <= Board.BOARD_SIZE - 1; columnIndex++, rowIndex--) {
				if (columnIndex == destination.getColumn() && rowIndex == destination.getRow()) {
					isValid = true;
					}
			}
		}
		return isValid;
	}
}