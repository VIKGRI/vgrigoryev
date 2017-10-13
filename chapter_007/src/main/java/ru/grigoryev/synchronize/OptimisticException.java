package ru.grigoryev.synchronize;

/**
 *Class represent the exception which occures
 * when attempting to rewrite data.
 *@author vgrigoryev
 *@since 13.10.2017
 *@version 1
 */
public class OptimisticException extends RuntimeException {
    /**
     *Constructor.
     *@param msg message to the user
     */
    public OptimisticException(String msg) {
        super(msg);
    }
}
