package com.grigoryev;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class represents executor which
 * processes messages.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class Executor {
    /**
     * Executor id.
     */
    private int id;
    /**
     * File path for executor to write information.
     */
    private Path executorFile;
    /**
     * Number of processed messages.
     */
    private AtomicInteger messageCount;
    /**
     * Delay time.
     */
    private int delay;
    /**
     * Lock.
     */
    private Lock lock;

    /**
     * Constructor.
     *
     * @param id executor id
     * @param executorDirPath file path for executor to write information
     * @param delay delay time
     * @throws IOException io exception
     */
    public Executor(int id, String executorDirPath, int delay) throws IOException {
        this.id = id;
        String fileSeparator = System.getProperty("file.separator");
        File file = new File(String.format("%1$s%2$s%3$s.txt", executorDirPath.toString(), fileSeparator, id));
        if (!file.exists()) {
            file.createNewFile();
        }
        this.executorFile = file.toPath();
        this.messageCount = new AtomicInteger();
        this.delay = delay;
        this.lock = new ReentrantLock();
    }

    /**
     * Processes message from a client.
     *
     * @param message message to process
     * @throws IOException
     * @throws InterruptedException
     */
    public void processMessage(Message message) throws IOException, InterruptedException {
        lock.lock();
        try {
            String data = String.format("Message #%1$s / Client: %2$s / Location: %3$s\n",
                    this.messageCount.getAndIncrement(), message.getClientId(), message.getPath());
            Files.write(this.executorFile, data.getBytes(), StandardOpenOption.APPEND);
            Thread.sleep(delay);
        } finally {
            lock.unlock();
        }
    }
}
