package com.grigoryev;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents client who
 * sends messages to the dispatcher.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class Client implements MessagePublisher, Runnable {
    /**
     * Client's id.
     */
    private int id;
    /**
     * Message's id.
     */
    private Message message;
    /**
     * List of listeners whom client sends the messages to.
     */
    private List<MessageListener> listeners;

    /**
     * Constructor.
     * @param id client's id
     * @param message message for dispatcher
     */
    public Client(int id, Message message) {
        this.id = id;
        this.message = message;
        this.listeners = new ArrayList<>();
    }

    /**
     * Gets client's id.
     * @return client's id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets client's id.
     * @param id client's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets client's message.
     * @return client's message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets client's message.
     * @param message client's message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void addListener(MessageListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(MessageListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void sendMessage() {
        for (MessageListener listener: this.listeners) {
            listener.dispatchMessage(this.message);
        }
    }

    @Override
    public void run() {
        this.sendMessage();
    }
}
