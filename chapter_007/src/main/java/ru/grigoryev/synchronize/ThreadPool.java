package ru.grigoryev.synchronize;

import java.util.ArrayList;
import java.util.List;

/**
 *Class provides thread pool for executing work.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class ThreadPool {
    /**
     * Queue which contains work tasks for executing by different threads.
     */
    private BlockingQueue<Work> workQueue;
    /**
     * List of threads in the current thread pool.
     */
    private List<WorkThread> threads;
    /**
     * Status of thread pool execution.
     */
    private boolean isStopped = false;
    /**
     * Maximum number of tasks in the work queue.
     */
    public static final int NUMBER_OF_TASKS = 10;
    /**
     * Number of threads created in the thread pool.
     */
    public static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    /**
     * Constructor.
     */
    public ThreadPool() {
        this.workQueue = new BlockingQueue(NUMBER_OF_TASKS);
        this.threads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            this.threads.add(new WorkThread(this.workQueue));
        }
        for (WorkThread thread : this.threads) {
            thread.start();
        }
    }

    /**
     * Adds work to the thread pool and executes it if there is an idle thread.
     * @param work work to execute
     * @throws IllegalStateException exception is thrown when
     * attempting to add work to the stopped thread pool
     */
    public synchronized void  add(Work work) throws IllegalStateException {
        if (this.isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        }
        this.workQueue.enqueue(work);
    }

    /**
     * Stops thread pool execution.
     */
    public synchronized void stop() {
        this.isStopped = true;
        for (WorkThread thread : this.threads) {
            thread.stopThread();
        }
    }
}
