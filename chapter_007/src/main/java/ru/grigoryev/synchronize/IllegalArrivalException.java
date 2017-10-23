package ru.grigoryev.synchronize;

/**
 *Class represent the exception which occures
 * when attempting to set invalid time interval.
 *@author vgrigoryev
 *@since 23.10.2017
 *@version 1
 */
public class IllegalArrivalException extends RuntimeException {
    /**
     *Constructor.
     *@param msg message to the user
     */
    public IllegalArrivalException(String msg) {
        super(msg);
    }
}
