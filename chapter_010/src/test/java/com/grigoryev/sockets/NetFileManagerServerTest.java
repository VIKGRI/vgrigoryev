package com.grigoryev.sockets;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class provides testing for
 * NetFileManagerServer class
 * functionality.
 *
 * @author vgrigoryev
 * @since 25.12.2017
 */
public class NetFileManagerServerTest {

    private static final String LN = System.getProperty("line.separator");

    /**
     * Exit functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenTypeExitThenServerExits() throws IOException {
        this.testServer("EXIT", String.format("%s", LN));
    }

    /**
     * Send all files in the directory functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenCommandDirlistThenServerSendsThreeFiles() throws IOException {
        this.testServer(
                Joiner.on(LN).join(
                        "DIRLIST",
                        "EXIT"),
                Joiner.on(LN).join(
                        "Folder1 DIR",
                        "Folder2 DIR",
                        "MyFile1.txt",
                        "",
                        "",
                        ""
                )
        );
    }

    /**
     * "Go to sub-directory" functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenCommandSubDirThenServerSendsFolderOne() throws IOException {
        this.testServer(
                Joiner.on(LN).join(
                        "SUBDIR Folder1",
                        "EXIT"),
                Joiner.on(LN).join(
                        "Root\\Folder1",
                        "",
                        "",
                        ""
                )
        );
    }

    /**
     * "Go to parent directory" functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenCommandParentThenServerSendsThatItIsAlreadyRoot() throws IOException {
        this.testServer(
                Joiner.on(LN).join(
                        "PARENT",
                        "EXIT"),
                Joiner.on(LN).join(
                        "You are in the root",
                        "",
                        "",
                        ""
                )
        );
    }

    /**
     * Download file functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenDownloadFileThenServerSendsSendsOperationSuccessfulStatus() throws IOException {
        Path testDirPath = Paths.get(String.format("%s", System.getProperty("user.dir")));
        Path testDir = Files.createTempDirectory(testDirPath, "tmp");
        Path testFile = Paths.get(System.getProperty("user.dir")).resolve("Root\\MyFile1.txt").normalize();
        this.testServer(
                Joiner.on(LN).join(
                        String.format("DOWNLOAD %1$s %2$s", testFile.getFileName().toString(), testDir.toString()),
                        "EXIT"),
                Joiner.on(LN).join(
                        "File is downloaded",
                        "",
                        "",
                        ""
                )
        );

        // Check downloaded file
        try (FileReader fr = new FileReader(testDir.resolve("MyFile1.txt").toFile());
             BufferedReader reader = new BufferedReader(fr)) {

            String line;
            String result = "";
            boolean fileMatches = false;
            while ((line = reader.readLine()) != null) {
                result = line;
            }
            if (result.trim().equals("Hello world")) {
                fileMatches = true;
            }
            assertThat(fileMatches, is(true));
        }
    }

    /**
     * Load file functionality testing.
     *
     * @throws IOException exception
     */
    @Test
    public void whenLoadFileThenServerSendsSendsFileContent() throws IOException {
        Path testFile = Paths.get(System.getProperty("user.dir")).resolve("Root\\MyFile1.txt").normalize();
        this.testServer(
                Joiner.on(LN).join(
                        String.format("LOAD %1$s", testFile.getFileName().toString()),
                        "EXIT"),
                Joiner.on(LN).join(
                        "Hello world",
                        "",
                        "",
                        ""
                )
        );
    }

    /**
     * Provides essential logic for testing.
     *
     * @param input    client input
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

        Path rootPath = Paths.get(new File(".").getAbsolutePath()).resolve("Root").normalize();

        NetFileManagerServer server = new NetFileManagerServer(socket, rootPath.toString());

        server.start(true);

        assertThat(out.toString(), is(expected));
    }
}