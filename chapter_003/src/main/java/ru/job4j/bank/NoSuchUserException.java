package ru.job4j.bank;

/**
*Class represent the exception which occures when user is not found.
*@author vgrigoryev
*@since 19.09.2017
*@version 1
*/
public class NoSuchUserException extends Exception {
	/**
	*Constructor with parameters.
	*@param msg message to the user
	*/
	NoSuchUserException(String msg) {
		super(msg);
	}
}