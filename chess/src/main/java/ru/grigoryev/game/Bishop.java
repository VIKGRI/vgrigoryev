package ru.grigoryev.game;

/**
*Class represent the bishop figure in the chess game.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public class Bishop extends Figure {
	/**
	*Constructor with parameters.
	*@param position Position of the figure
	 *@param color Color of the figure
	*/
	public Bishop(Cell position, Color color) {
		super(position, color);
		this.setMoveBehavior(new MoveAsBishop());
	}
	/**
	*This method is used for identifying figure.
	*@return figure type
	*/
	public String getFigureType() {
		return "Bishop";
	}
	/**
	*This method is used for creating a new figure.
	*@param cell location of figure
	 *@param color color of figure
	*@return new figure
	*/
	public Figure clone(Cell cell, Color color) {
		return new Bishop(cell, color);
	}
}