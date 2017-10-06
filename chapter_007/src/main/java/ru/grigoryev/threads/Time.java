package ru.grigoryev.threads;

/**
 *Class for measuring program execution time.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class Time implements Runnable {
    /**
     * Limit time for program execution.
     */
    private long limitTime;

    /**
     * Constructor.
     * @param limitTime Limit time for program execution
     */
    public Time(long limitTime) {
        this.limitTime = limitTime;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < this.limitTime) {
            if (Thread.interrupted()) {
                return;
            }
        }
    }
}
