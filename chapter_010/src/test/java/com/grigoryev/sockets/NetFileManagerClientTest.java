package com.grigoryev.sockets;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class provides testing for
 * NetFileManagerClient class
 * functionality.
 *
 * @author vgrigoryev
 * @since 25.12.2017
 */
public class NetFileManagerClientTest {
    /**
     * Line separator in the corresponding OS.
     */
    private static final String LN = System.getProperty("line.separator");
    /**
     * Denotes console output stub for testing.
     * This output is sent to the console stub.
     */
    private static final int CONSOLE_OUTPUT_STUB = 1;
    /**
     * Denotes server output stub for testing.
     * This output is sent to the server stub.
     */
    private static final int SERVER_OUTPUT_STUB = 2;

    /**
     * Command input functionality testing.
     * Sunny case. Legal command.
     *
     * @throws IOException exception
     */
    @Test
    public void whenClientEntersLegalCommandDownloadThenRequestToServerIsFormed() throws IOException {
        this.testClientInput(
                Joiner.on(LN).join(
                        "File is downloaded.",
                        "",
                        "",
                        ""
                ),
                Joiner.on(LN).join(
                        String.format("DOWNLOAD File %1$s%2$sEXIT %3$s",
                                Paths.get(new File(".").getAbsolutePath()).normalize(),
                                LN,
                                Paths.get(new File(".").getAbsolutePath()).normalize()),
                        ""
                ),
                Joiner.on(LN).join(
                        "DOWNLOAD File",
                        "EXIT"
                ),
                NetFileManagerClientTest.SERVER_OUTPUT_STUB
        );
    }

    /**
     * Command input functionality testing.
     * Failed case. Illegal command.
     *
     * @throws IOException exception
     */
    @Test
    public void whenClientEntersIllegalCommandThenWarningIsThrownToConsole() throws IOException {
        String cmdPrompt = "CURRENT FOLDER: C:\\projects\\vgrigoryev\\chapter_010 > ";

        this.testClientInput(
                Joiner.on(LN).join(
                        "",
                        "",
                        "",
                        ""
                ),

                String.format("%1$s%2$sThe command is illegal%3$s%4$s%5$s",
                        cmdPrompt,
                        LN,
                        LN,
                        cmdPrompt,
                        LN),
                Joiner.on(LN).join(
                        "some illegal command",
                        "EXIT"
                ),
                NetFileManagerClientTest.CONSOLE_OUTPUT_STUB
        );
    }

    /**
     * Provides essential logic for testing.
     *
     * @param input       client input
     * @param expected    expected output
     * @param clientInput client input
     * @throws IOException i/o exception
     */
    private void testClientInput(String input, String expected, String clientInput, int outputStubKey) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayInputStream clientIn = new ByteArrayInputStream(clientInput.getBytes());
        ByteArrayOutputStream serverOutput = new ByteArrayOutputStream();
        ByteArrayOutputStream clientOut = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(serverOutput);
        NetFileManagerClient client = new NetFileManagerClient(socket, clientOut, clientIn);
        String currentFolderPath = System.getProperty("user.dir");
        client.setCurrentPath(currentFolderPath);
        client.start(true);
        ByteArrayOutputStream output;
        if (outputStubKey == NetFileManagerClientTest.CONSOLE_OUTPUT_STUB) {
            output = clientOut;
        } else {
            output = serverOutput;
        }
        assertThat(output.toString(), is(expected));
    }
}