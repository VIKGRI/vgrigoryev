package ru.grigoryev.game;

/**
*Class represent the exception which occures when the figure is not able move to the specified position.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public class OccupiedWayException extends Exception {
	/**
	*Constructor with parameters.
	*@param msg message to the user
	*/
	public OccupiedWayException(String msg) {
		super(msg);
	}
}