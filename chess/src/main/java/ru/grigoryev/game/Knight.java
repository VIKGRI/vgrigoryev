package ru.grigoryev.game;

/**
*Class represent the knight figure in the chess game.
*@author vgrigoryev
*@since 17.09.2017
*@version 1
*/
public class Knight extends Figure {
	/**
	*Constructor with parameters.
	*@param position Position of the figure
	 *@param color Color of the figure
	*/
	public Knight(Cell position, Color color) {
		super(position, color);
		this.setMoveBehavior(new MoveAsKnight());
	}
	/**
	*This method is used for identifying figure.
	*@return figure type
	*/
	public String getFigureType() {
		return "Knight";
	}
	/**
	*This method is used for creating a new figure.
	*@param cell location of figure
	 *@param color color of figure
	*@return new figure
	*/
	public Figure clone(Cell cell, Color color) {
		return new Knight(cell, color);
	}
}