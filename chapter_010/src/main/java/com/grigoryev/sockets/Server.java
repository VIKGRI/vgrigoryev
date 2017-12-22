package com.grigoryev.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class represents server.
 * Sends answers to a client taken from
 * file randomly.
 *
 * @author vgrigoryev
 * @since 22.12.2017
 */
public class Server {
    /**
     * Server port.
     */
    private final Socket socket;
    /**
     * Key word to exit server.
     */
    private String exitKey;

    /**
     * Constructor.
     *
     * @param socket Server socket
     * @param exitKey Key word to exit server
     */
    public Server(Socket socket, String exitKey) {
        this.socket = socket;
        this.exitKey = exitKey;
    }

    /**
     * Starts server.
     * @param sourceFile file to get answers from
     */
    public void start(String sourceFile) {
            try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                 RandomAccessFile fileReader = new RandomAccessFile(sourceFile, "r")) {
                String ask;
                long byteCount = fileReader.length();
                do {
                    System.out.println("wait command ...");
                    ask = in.readLine();
                        System.out.println(String.format("Client: %s", ask));
                        if ("hello".equals(ask)) {
                            out.println("Hello, dear friend, I'm a oracle.");
                            out.println();
                        } else if (!this.exitKey.equals(ask)) {
                            String rs = randomStringFromFile(fileReader, byteCount);
                            out.println(rs);
                            out.println();
                        } else {
                            System.out.println("server exits");
                        }
                } while (!this.exitKey.equals(ask));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }

    /**
     * Auxiliary method which selects random string from a file.
     * @param reader specified random access file stream
     * @param byteCount number of bytes in the file
     * @return chosen string
     * @throws IOException exception
     */
    private static String randomStringFromFile(RandomAccessFile reader, long byteCount) throws IOException {
        long random = (long) (Math.random() * byteCount);
        reader.seek(random);
        if (reader.readChar() != '\n') {
            char ch = ' ';
            while (ch != '\n' && random >= 1) {
                random -= 1;
                reader.seek(random);
                ch = (char) reader.readByte();
            }
            if (random == 0) {
                reader.seek(0);
            }
        }
        return reader.readLine();
    }

    /**
     * main.
     * @param args arguments
     */
    public static void main(String[] args) throws IOException {
        try (final Socket socket = new ServerSocket(9999).accept()) {
            Server server = new Server(socket, "exit");
            server.start("src\\resources\\testOracleAnswers.txt");
        }
    }
}
