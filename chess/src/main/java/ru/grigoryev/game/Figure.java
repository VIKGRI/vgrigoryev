package ru.grigoryev.game;

/**
*Abstract class represent the figure in the chess game.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public abstract class Figure {
	/**
	*Current position of the figure.
	*/
	private final Cell position;
	/**
	*The color of figure.
	*/
	private Color color;
	/**
	*Represents the way in which figure makes a move.
	*/
	private MoveBehavior moveBehavior;
	/**
	*Constructor with parameters.
	*@param position Position of the figure
	 *@param color Color of the figure
	*/
	public Figure(Cell position, Color color) {
		this.position = position;
		this.color  = color;
	}
	/**
	*This method set the current movement behavior of figure.
	*@param moveBehavior movement behavior of figure
	*/
	public void setMoveBehavior(MoveBehavior moveBehavior) {
		if (moveBehavior != null) {
			this.moveBehavior = moveBehavior;
		}
	}
	/**
	*This method provides getting color of figure.
	*@return color of figure
	*/
	public Color getColor() {
		return this.color;
	}
	/**
	*This method is used for moving the figure.
	*@param destination The cell where the figure is supposed to locate
	*@return array of cells which represents the way to destination
	*@throws ImpossibleMoveException .
	*/
	public Cell[] performMove(Cell destination) throws ImpossibleMoveException {
		return moveBehavior.way(position, destination);
	}
	/**
	*This method is used for moving the figure.
	*@param other other cell
	*@return true if the figure is in the other cell
	*/
	public boolean isPositionSame(Cell other) {
		return this.position.getColumn() == other.getColumn() && this.position.getRow() == other.getRow();
	}
	/**
	*This method is used for identifying figure.
	*@return figure type
	*/
	public abstract String getFigureType();
	/**
	*This method is used for creating a new figure.
	*@param cell location of figure
	 *@param color color of figure
	*@return new figure
	*/
	 public abstract Figure clone(Cell cell, Color color);
}
/**
*Enumeration that represents the figure's color.
*@author vgrigoryev
*@since 19.09.2017
*@version 1
*/
enum Color {
	/**
	 * represents colors used in this game.
	 */
	WHITE, BLACK
}