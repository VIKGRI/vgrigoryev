package ru.grigoryev.game;

/**
*Class represent the position of the figure in the chess game.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public class Cell {
	/**
	*Number of column of cell.
	*/
	private int column;
	/**
	*Number of row of cell.
	*/
	private int row;
	/**
	*Returns number of column.
	@return number of column
	*/
	int getColumn() {
		return column;
	}
	/**
	*Returns number of row.
	@return number of row
	*/
	int getRow() {
		return row;
	}
	/**
	*Constructor with parameters.
	*@param column number of column
	*@param row number of row
	*/
	public Cell(int column, int row) {
		this.column = column;
		this.row = row;
	}
}