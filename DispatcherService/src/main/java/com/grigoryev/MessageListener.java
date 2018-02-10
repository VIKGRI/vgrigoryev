package com.grigoryev;

/**
 * Message listener.
 * Represents Observer pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public interface MessageListener {
    /**
     * Dispatches the message when gets it.
     * @param message message sent from publisher
     */
    void dispatchMessage(Message message);
}
