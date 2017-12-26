package com.grigoryev.sockets;

/**
 * Enumeration which represents
 * commands from client to server.
 *
 * @author vgrigoryev
 * @since 23.12.2017
 */
public enum Command {
    DIRLIST, SUBDIR, PARENT, DOWNLOAD, LOAD, EXIT
}
