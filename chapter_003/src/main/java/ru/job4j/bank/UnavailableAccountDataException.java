package ru.job4j.bank;

/**
*Class represent the exception which occures when neither the requisites nor the value of Account are correct.
*@author vgrigoryev
*@since 19.09.2017
*@version 1
*/
public class UnavailableAccountDataException extends Exception {
	/**
	*Constructor with parameters.
	*@param msg message to the user
	*/
	UnavailableAccountDataException(String msg) {
		super(msg);
	}
}