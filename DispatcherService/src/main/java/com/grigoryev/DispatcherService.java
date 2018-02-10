package com.grigoryev;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class represents dispatcher service
 * which responsible for accepting messages
 * from the clients and delegate message
 * processing to the executors.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class DispatcherService implements MessageListener {
    /**
     * Provides id for message.
     */
    private AtomicInteger messageIdGenerator;
    /**
     * Lock.
     */
    private Lock lock;
    /**
     * Stores executors with corresponding ids.
     */
    private Map<Integer, Executor> executors;
    /**
     * Provides thread pooling.
     */
    private ExecutorService service;
    /**
     * Root directory for executors to store messages.
     */
    private Path executorDirectory;
    /**
     * Provides information whether the message processing is completed or not.
     */
    private Map<Integer, Future<Integer>> responseStatus;
    /**
     * Delay for executors.
     */
    private static final int DELAY = 3000;
    /**
     * Number of threads.
     */
    private static final int THREAD_NUM = 5;

    /**
     * Constructor.
     * @param executorsNum number of executors
     * @throws IOException io exception
     */
    public DispatcherService(int executorsNum) throws IOException {
        this.messageIdGenerator = new AtomicInteger();
        this.lock = new ReentrantLock();
        this.service = Executors.newFixedThreadPool(THREAD_NUM);
        File file = new File("executors");
        if (!file.exists()) {
            file.mkdir();
        }
        this.executorDirectory = file.toPath();
        this.responseStatus = new HashMap<>();
        this.executors = this.createExecutors(executorsNum);
    }

    /**
     * Gets response status.
     * @return information whether the message processing is completed or not
     */
    public Map<Integer, Future<Integer>> getResponseStatus() {
        return this.responseStatus;
    }

    /**
     * Creates executors.
     * @param executorsNum number of executors
     * @return executors with corresponding ids
     */
    private Map<Integer, Executor> createExecutors(int executorsNum) throws IOException {
        Map<Integer, Executor> executors = new HashMap<>();
        for (int i = 0; i < executorsNum; i++) {
            int id = i + 1;
            Executor executor = new Executor(id, this.executorDirectory.toString(), DELAY);
            executors.put(id, executor);
        }
        return executors;
    }

    @Override
    public void dispatchMessage(Message message) {
        try {
            lock.lock();
            int id = this.messageIdGenerator.getAndIncrement();
            Future<Integer> taskStatus = this.service.submit(() -> {
                XmlHandler handler = new XmlMessageHandler();
                int executorId = handler.parseXml(message.getPath());
                handler.assignId(message.getPath(), id);
                Executor executor = this.executors.get(executorId);
                if (executor != null) {
                    executor.processMessage(message);
                }
                return message.getClientId();
            });
            this.responseStatus.put(id, taskStatus);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Wait for all threads complete.
     */
    public void waitTaskCompletion() throws ExecutionException, InterruptedException {
        for (Map.Entry<Integer, Future<Integer>> future: this.responseStatus.entrySet()) {
            future.getValue().get();
        }
    }
}
