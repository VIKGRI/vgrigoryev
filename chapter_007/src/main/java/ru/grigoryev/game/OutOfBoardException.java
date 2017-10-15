package ru.grigoryev.game;

/**
 *Class represent the exception which occures
 * when attempting to rewrite data.
 *@author vgrigoryev
 *@since 13.10.2017
 *@version 1
 */
public class OutOfBoardException extends RuntimeException {
    /**
     *Constructor.
     *@param msg message to the user
     */
    public OutOfBoardException(String msg) {
        super(msg);
    }
}
