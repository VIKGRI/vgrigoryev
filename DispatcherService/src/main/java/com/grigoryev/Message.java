package com.grigoryev;

/**
 * Represents message contract.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public interface Message {
    /**
     * Gets path where the message file resides.
     * @return path
     */
    String getPath();

    /**
     * Gets id of client who sent the message.
     * @return client id
     */
    int getClientId();
}
