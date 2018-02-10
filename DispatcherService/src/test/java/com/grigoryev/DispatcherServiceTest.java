package com.grigoryev;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class provides testing
 * for DispatcherService-ClientService
 * system.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class DispatcherServiceTest {
    /**
     * Amount of executors in the test.
     */
    private static final int EXECUTORS_NUMBER = 10;
    /**
     * Amount of clients in the test.
     */
    private static final int CLIENT_NUMBER = 100;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServiceTest.class);
    /**
     * Testing DispatcherService and ClientService.
     * Client service creates 100 clients and launches sending messages.
     * DispatcherService creates 10 executors,  accepts messages and delegates processing to
     * the executors. Executors write message information with client id who sends message.
     */
    @Test
    public void whenSend100MessagesThenExecutorsWrite100clientIdsToFile() {
        try {
            DispatcherService dispatcher = new DispatcherService(EXECUTORS_NUMBER);
            ClientService clientService = new ClientService(CLIENT_NUMBER, EXECUTORS_NUMBER, dispatcher);
            clientService.createClients();
            clientService.launchClients();
            dispatcher.waitTaskCompletion();

            Set<Integer> expectedIds = new HashSet<>();
            for (int i = 1; i <= CLIENT_NUMBER; i++) {
                expectedIds.add(i);
            }

            DispatcherServiceTest test = new DispatcherServiceTest();
            Set<Integer> resultIds = test.readAllExecutorFiles();

            assertThat(resultIds, is(expectedIds));
        } catch (IOException | InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Set<Integer> readIdsFromExecutorResultFile(Path filePath) {
        Set<Integer> ids = new HashSet<>();
        try (InputStream in = Files.newInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("/");
                String clientId = tokens[1].trim().split(":")[1].trim();
                ids.add(Integer.valueOf(clientId));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ids;
    }

    private Set<Integer> readAllExecutorFiles() {
        Set<Integer> ids = new HashSet<>();
        String fileSeparator = System.getProperty("file.separator");
        for (int i = 1; i <= EXECUTORS_NUMBER; i++) {
            ids.addAll(this.readIdsFromExecutorResultFile(Paths.get(String.format("executors%1$s%2$s.txt", fileSeparator, i))));
        }
        return ids;
    }
}