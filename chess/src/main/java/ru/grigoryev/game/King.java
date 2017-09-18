package ru.grigoryev.game;

/**
*Class represent the king figure in the chess game.
*@author vgrigoryev
*@since 18.09.2017
*@version 1
*/
public class King extends Figure {
	/**
	*Constructor with parameters.
	*@param position Position of the figure
	 *@param color Color of the figure
	*/
	public King(Cell position, Color color) {
		super(position, color);
		this.setMoveBehavior(new MoveAsKing());
	}
	/**
	*This method is used for identifying figure.
	*@return figure type
	*/
	public String getFigureType() {
		return "King";
	}
	/**
	*This method is used for creating a new figure.
	*@param cell location of figure
	 *@param color color of figure
	*@return new figure
	*/
	public Figure clone(Cell cell, Color color) {
		return new King(cell, color);
	}
}