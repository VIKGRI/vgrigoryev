package com.grigoryev.sockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * Class represents client
 * using server file system.
 *
 * @author vgrigoryev
 * @since 24.12.2017
 */
public class NetFileManagerClient {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(NetFileManagerClient.class);
    /**
     * Server socket.
     */
    private final Socket socket;
    /**
     * Exit key word.
     */
    private String exitKey;
    /**
     * Output stream for server messages.
     */
    private OutputStream clientOutput;
    /**
     * Input stream from where user input is got.
     */
    private InputStream clientInput;
    /**
     * Current folder path
     */
    private String currentPath;
    /**
     * Project path.
     */
    private Path projectPath;

    /**
     * Constructor.
     *
     * @param socket       socket for connection to server
     * @param clientOutput client output stream
     * @param clientInput  client input stream
     */
    public NetFileManagerClient(Socket socket, OutputStream clientOutput, InputStream clientInput) {
        this.socket = socket;
        this.exitKey = "exit";
        this.clientOutput = clientOutput;
        this.clientInput = clientInput;
        this.currentPath = null;
        this.projectPath = Paths.get(new File(".").getAbsolutePath()).normalize();
    }

    /**
     * Gets project path.
     * @return project path
     */
    public Path getProjectPath() {
        return projectPath;
    }

    /**
     * Launches client.
     * @param testingMode Disables reading root directory path from the server if true.
     */
    public void start(boolean testingMode) {
        try (PrintWriter writerToServer = new PrintWriter(this.socket.getOutputStream(), true);
             BufferedReader responseReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
             Scanner clientInput = new Scanner(this.clientInput, "UTF-8").useDelimiter("line.separator");
             PrintWriter clientOutput = new PrintWriter(this.clientOutput, true)) {

            String serverResponse;
            String clientRequest;
            String commandName;

            if (!testingMode) {
                // Gets root direction from the server.
                serverResponse = responseReader.readLine();
                this.currentPath = serverResponse;
            }
            String rootPath = String.format("%1$s\\", this.currentPath);

            do {
                clientOutput.println(String.format("CURRENT FOLDER: %s > ", this.currentPath));
                clientRequest = clientInput.nextLine().trim();
                String[] command = clientRequest.split(" ");
                commandName = command[0];
                if (!clientRequest.isEmpty()) {
                    if (this.isValidInput(commandName)) {
                        writerToServer.println(String.format("%1$s %2$s", clientRequest, this.projectPath));
                        serverResponse = responseReader.readLine().trim();
                        while (!serverResponse.isEmpty()) {
                            if (serverResponse.startsWith(rootPath)) {
                                this.currentPath = serverResponse;
                            } else {
                                clientOutput.println(serverResponse);
                            }
                            serverResponse = responseReader.readLine();
                        }
                    } else {
                        clientOutput.println("The command is illegal");
                    }
                }
            } while (!commandName.equals(Command.EXIT.toString()));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verifies client input.
     *
     * @param input client input.
     * @return
     */
    private boolean isValidInput(String input) {
        Command[] commands = Command.values();
        boolean contains = false;
        for (Command com : Arrays.asList(commands)) {
            if (com.toString().equals(input.toUpperCase())) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * main.
     *
     * @param args arguments
     */
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src\\app.properties"))) {
            props.load(reader);
            try (Socket socket = new Socket(InetAddress.getByName(props.getProperty("ip")), Integer.valueOf(props.getProperty("port")))) {
                NetFileManagerClient client = new NetFileManagerClient(socket, System.out, System.in);
                client.start(false);
            }
        }
    }

    /**
     * This method is used for testing.
     * @param path current folder path in the net file system
     */
    public void setCurrentPath(String path) {
        this.currentPath = path;
    }
}
