package com.grigoryev.sockets;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class provides testing for client.
 *
 * @author vgrigoryev
 * @since 22.12.2017
 */
public class ClientTest {
    private static final String LN = System.getProperty("line.separator");

    @Test
    public void whenServerSendsGreetingThenServerPrintsItOut() throws IOException {
        this.testClient(
                Joiner.on(LN).join(
                        "Hello, dear friend, I'm a oracle.",
                        "",
                        ""
                ),
                Joiner.on(LN).join(
                        "Hello, dear friend, I'm a oracle.",
                        ""
                ),
                Joiner.on(LN).join(
                        "Hello, dear friend, I'm a oracle.",
                        "exit"
                )
        );
    }

    /**
     * Provides essential logic for testing.
     *
     * @param input    client input
     * @param expected expected output
     * @param clientInput client input
     * @throws IOException i/o exception
     */
    private void testClient(String input, String expected, String clientInput) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayInputStream clientIn = new ByteArrayInputStream(clientInput.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream clientOut = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        Client client = new Client(socket, "exit", clientOut, clientIn);

        client.start();

        assertThat(clientOut.toString(), is(expected));
    }
}