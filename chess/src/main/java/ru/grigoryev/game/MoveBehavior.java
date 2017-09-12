package ru.grigoryev.game;

/**
*Interface represents movement behavior of the figure.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public interface MoveBehavior {
	/**
	*This method validates whether it is posible to move the figure in the destination cell or not.
	*@param source the position from where to remove the figure
	*@param destination the position where to place the figure
	*@return The array of Cells which the figure should pass through to reach the destination
	*@throws ImpossibleMoveException if the movement is not available for this type of figure
	*/
	Cell[] way(Cell source, Cell destination) throws ImpossibleMoveException;
	/**
	*This method validates whether it is posible to move the figure in the destination cell or not.
	*@param source the position from where to remove the figure
	*@param destination the position where to place the figure
	*@return true if it is posible to move the figure in the destination cell and false otherwise
	*/
	boolean validate(Cell source, Cell destination);
}