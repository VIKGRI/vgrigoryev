package com.grigoryev;

/**
 * Message publisher. Sends messages to all listeners (subscribers).
 * Represents Observer pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public interface MessagePublisher {
    /**
     * Adds listener.
     * @param listener listener
     */
    void addListener(MessageListener listener);

    /**
     * Removes listener.
     * @param listener listener
     */
    void removeListener(MessageListener listener);

    /**
     * Sends message.
     */
    void sendMessage();
}
