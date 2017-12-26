package com.grigoryev.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Class represents server.
 * Provides file management
 * system for client.
 *
 * @author vgrigoryev
 * @since 24.12.2017
 */
public class NetFileManagerServer {
    /**
     * Server port.
     */
    private final Socket socket;
    /**
     * Root directory path.
     */
    private Path rootPath;

    /**
     * Constructor.
     *
     * @param socket   socket connected to client
     * @param rootPath root path of file manager
     */
    public NetFileManagerServer(Socket socket, String rootPath) {
        this.socket = socket;
        this.rootPath = Paths.get(rootPath);
    }

    /**
     * Launches the server.
     *
     * @param testingMode Disables sending root directory path to the client if true.
     * @throws IOException IOException exception related to input/output
     */
    public void start(boolean testingMode) throws IOException {
        try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            if (!testingMode) {
                // Sends root folder path to a client.
                out.println(this.rootPath.getFileName().toString());
            }
            String clientCommand;
            String commandName;
            CommandDispatcher dispatcher = new CommandDispatcher(out, this.rootPath).init();
            do {
                System.out.println("wait command ...");
                clientCommand = in.readLine();
                dispatcher.perform(clientCommand);
                String[] command = clientCommand.split(" ");
                commandName = command[0];
            } while (!commandName.equals(Command.EXIT.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * main method.
     *
     * @param args arguments
     * @throws IOException exception related to input/output
     */
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src\\app.properties"))) {
            props.load(reader);
            try (Socket socket = new ServerSocket(Integer.valueOf(props.getProperty("port"))).accept()) {
                NetFileManagerServer server
                        = new NetFileManagerServer(
                                socket, "C:\\Users\\admin\\Desktop\\cpp\\java\\Курс\\Pre-middle"
                        );
                server.start(false);
            }
        }
    }
}