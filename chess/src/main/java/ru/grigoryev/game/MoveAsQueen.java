package ru.grigoryev.game;

/**
 * The class implements MoveBehavior interface and represents movement behavior as bishop figure has.
 *
 * @author vgrigoryev
 * @version 1
 * @since 11.09.2017
 */
public class MoveAsQueen implements MoveBehavior {
    /**
     * This method validates whether it is posible to move the figure in the destination cell or not.
     *
     * @param source      the position from where to remove the figure
     * @param destination the position where to place the figure
     * @return The array of Cells which the figure should pass through to reach the destination
     * @throws ImpossibleMoveException if the movement is not available for this type of figure
     */
    public Cell[] way(Cell source, Cell destination) throws ImpossibleMoveException {
        /*
        *Если фигура так не ходит, выбрасываем исключение.
		*/
        if (!this.validate(source, destination)) {
            throw new ImpossibleMoveException("Bishop is not able to move in this destination");
        }
        int wayLength = 0;
        Cell[] way = null;

        if (destination.getColumn() == source.getColumn() || destination.getRow() == source.getRow()) {
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
            way = new Cell[wayLength];
            if (destination.getColumn() == source.getColumn()) {
                for (int k = 0; startIndex < endIndex; startIndex++, k++) {
                    way[k] = new Cell(destination.getColumn(), startIndex);
                }
            } else {
                for (int k = 0; startIndex < endIndex; startIndex++, k++) {
                    way[k] = new Cell(startIndex, destination.getRow());
                }
            }
        } else {

            if (destination.getColumn() > source.getColumn()) {
                wayLength = destination.getColumn() - source.getColumn();
            } else {
                wayLength = source.getColumn() - destination.getColumn();
            }
            way = new Cell[wayLength];
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
        }
        return way;
    }

    /**
     * This method validates whether it is posible to move the figure in the destination cell or not.
     *
     * @param source      the position from where to remove the figure
     * @param destination the position where to place the figure
     * @return true if it is posible to move the figure in the destination cell and false otherwise
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
		*на которой расположена фигура королева, и сравниваем с
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
        if (!isValid) {
            Cell[] allMoves = this.possibleMovesAsRookStyle(source); // получаем массив всех допустимых ходов с этой клетки
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
        }
        return isValid;
    }

    /**
     * This method return the array of the possible destinations of this Queen as Rook way of movement depending on source.
     *
     * @param source the position from where to remove the figure
     * @return the array of the possible destinations of Rook depending on source
     */
    private Cell[] possibleMovesAsRookStyle(Cell source) {
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
        return moves;
    }
}