package ru.job4j.bank;

/**
*Class represent the exception which occures when neither the name nor the passport are correct.
*@author vgrigoryev
*@since 19.09.2017
*@version 1
*/
public class UnavailableUserDataException extends Exception {
	/**
	*Constructor with parameters.
	*@param msg message to the user
	*/
	UnavailableUserDataException(String msg) {
		super(msg);
	}
}