package ru.grigoryev.synchronize;

/**
 *Class represents particular thread in the thread pool.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class WorkThread extends Thread {
    /**
     *
     */
    private BlockingQueue<Work> workQueue;
    /**
     * Status of the thread. Defines whether it is stopped or not.
     */
    private boolean isStopped = false;
    /**
     * Constructor.
     * @param queue Main queue which is used in the thread pool.
     */
    public WorkThread(BlockingQueue<Work> queue) {
        this.workQueue = queue;
    }

    @Override
    public void run() {
        while (!isStopped()) {
            try {
                Work work = workQueue.dequeue();
                work.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Stops execution of the thread.
     */
    public synchronized void stopThread() {
        isStopped = true;
        this.interrupt();
    }

    /**
     * Gets the status of the thread.
     * @return true if it is still executing.
     */
    public synchronized boolean isStopped() {
        return isStopped;
    }
}
