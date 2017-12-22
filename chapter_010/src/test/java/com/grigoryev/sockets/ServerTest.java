package com.grigoryev.sockets;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class provides testing for server.
 *
 * @author vgrigoryev
 * @since 22.12.2017
 */
public class ServerTest {

    private static final String LN = System.getProperty("line.separator");

    @Test
    public void whenTypeExitThenServerExits() throws IOException {
        this.testServer("exit", "");
    }

    @Test
    public void whenTypeHelloThenServerSentsGreeting() throws IOException {
        this.testServer(
                Joiner.on(LN).join(
                        "hello",
                        "exit"),
                Joiner.on(LN).join(
                        "Hello, dear friend, I'm a oracle.",
                        "",
                        ""
                )
        );
    }

    @Test
    public void whenTypeNotSpecifiedWordThenServerSendsRandonPhraseFromFile() throws IOException {
        this.testServer(
                Joiner.on(LN).join(
                        "what's up?",
                        "exit"
                ),
                Joiner.on(LN).join(
                        "first oracle answer",
                        "",
                        ""
                )
        );
    }

    /**
     * Provides essential logig for testing.
     * @param input client input
     * @param expected expected output
     * @throws IOException i/o exception
     */
    private void testServer(String input, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(
                input.getBytes()
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        Server server = new Server(socket, "exit");

        server.start("src\\resources\\testOracleAnswers.txt");

        assertThat(out.toString(), is(expected));
    }
}