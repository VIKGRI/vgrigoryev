package com.grigoryev;

/**
 * Provides information about message.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class XmlMessage implements Message {
    /**
     * File path where the message file resides.
     */
    private String filePath;
    /**
     * Id of the client who sent the message.
     */
    private int clientId;

    /**
     * Constructor.
     *
     * @param clientId client's id
     * @param filePath file path where the message file resides
     */
    public XmlMessage(int clientId, String filePath) {
        this.clientId = clientId;
        this.filePath = filePath;
    }

    @Override
    public String getPath() {
        return this.filePath;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}
