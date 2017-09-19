package ru.job4j.bank;

/**
*Class represent the exception which occures when somebody tries to rewrite existing Account.
*@author vgrigoryev
*@since 19.09.2017
*@version 1
*/
public class RewriteExistingAccountException extends Exception {
	/**
	*Constructor with parameters.
	*@param msg message to the user
	*/
	RewriteExistingAccountException(String msg) {
		super(msg);
	}
}