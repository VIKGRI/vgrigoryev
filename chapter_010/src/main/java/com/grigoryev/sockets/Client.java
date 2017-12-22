package com.grigoryev.sockets;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Class represents client.
 * Sends requests to a server.
 * Gets answers from a server.
 *
 * @author vgrigoryev
 * @since 22.12.2017
 */
public class Client {
    /**
     * Server socket.
     */
    private final Socket socket;
    /**
     * Exit word.
     */
    private String exitKey;
    /**
     * Output stream for server messages.
     */
    private OutputStream output;
    /**
     * Input stream from where user input is got.
     */
    private InputStream userInput;

    /**
     * Constructor.
     * @param socket server socket
     * @param exitKey exit key word
     * @param output output stream for server messages
     * @param userInput Input stream from where user input is got
     */
    public Client(Socket socket, String exitKey, OutputStream output, InputStream userInput) {
        this.socket = socket;
        this.exitKey = exitKey;
        this.output = output;
        this.userInput = userInput;
    }

    /**
     * Starts client.
     */
    public void start() {
        try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
             Scanner console = new Scanner(this.userInput, "UTF-8").useDelimiter("line.separator");
             PrintWriter writer = new PrintWriter(this.output, true)) {
            String serverResponse;
            String clientRequest;
            do {
                    clientRequest = console.nextLine();
                    if (!clientRequest.isEmpty()) {
                        out.println(clientRequest);
                        if (!clientRequest.equals(this.exitKey)) {
                            StringBuffer buffer = new StringBuffer();
                            serverResponse = in.readLine();
                            while (!serverResponse.isEmpty()) {
                                buffer.append(serverResponse);
                                buffer.append(" ");
                                serverResponse = in.readLine();
                            }
                            String buf = buffer.toString().trim();
                            writer.println(buffer.toString().trim());
                        }
                    }
            } while (!clientRequest.equals(this.exitKey));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * main.
     *
     * @param args arguments
     */
    public static void main(String[] args) throws IOException {
        Socket socket =  new Socket(InetAddress.getByName("localhost"), 9999);
        Client client = new Client(socket, "exit", System.out, System.in);
        client.start();
    }
}
