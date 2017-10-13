package ru.grigoryev.synchronize;

/**
 *Class represents mechanism of monitor locking.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class Lock {
    /**
     * Indicates whether the lock is locked by any thread or not.
     */
    private volatile boolean isLocked = false;

    /**
     * Locks the lock.
     * Enters the critical section.
     */
    public synchronized void lock() {
        while (this.isLocked) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        this.isLocked = true;
    }

    /**
     * Unlocks the lock.
     * Exits the critical section.
     */
    public synchronized void unlock() {
        this.isLocked = false;
        this.notify();
    }
}
