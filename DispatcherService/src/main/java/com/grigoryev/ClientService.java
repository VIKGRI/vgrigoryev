package com.grigoryev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class represents client service.
 * It's responsible for client creation,
 * message generation and launching sending
 * message process.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class ClientService {
    /**
     * Provides xml process functionality.
     */
    private XmlHandler xmlHandler;
    /**
     * List of clients.
     */
    private List<Client> clients;
    /**
     * Number of clients.
     */
    private int clientNum;
    /**
     * Number of executors.
     */
    private int executorNum;
    /**
     * Dispatcher.
     */
    private DispatcherService dispatcherService;
    /**
     * Provides choosing executor for particular task.
     */
    private Random random;

    /**
     * Constructor.
     *
     * @param clientNum         number of clients
     * @param executorNum       number of executors
     * @param dispatcherService dispatcher
     */
    public ClientService(int clientNum, int executorNum, DispatcherService dispatcherService) {
        this.xmlHandler = new XmlMessageHandler();
        this.clients = new ArrayList<>();
        this.clientNum = clientNum;
        this.executorNum = executorNum;
        this.dispatcherService = dispatcherService;
        this.random = new Random();
    }

    /**
     * Creates clients.
     */
    public void createClients() {
        File tmp = new File("tmp");
        if (!tmp.exists()) {
            tmp.mkdir();
        }
        String fileSeparator = System.getProperty("file.separator");
        for (int i = 0; i < clientNum; i++) {
            int clientId = i + 1;
            String fileName = String.valueOf(String.format("%1$s%2$s%3$s", tmp.toString(), fileSeparator, clientId));
            Message message = new XmlMessage(
                    clientId, this.xmlHandler.buildXml(fileName, random.nextInt(this.executorNum) + 1)
            );
            Client client = new Client(clientId, message);
            client.addListener(this.dispatcherService);
            this.clients.add(client);
        }
    }

    /**
     * Launches sending messages process.
     */
    public void launchClients() throws InterruptedException {
        for (Client client : this.clients) {
            Thread t = new Thread(client);
            t.start();
            t.join();
        }
    }
}