package ru.grigoryev.synchronize;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing thread safe ParallelSearch class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.10.2017
 */
public class LockTest {
    /**
     * value for testing Lock's methods.
     */
    private int value = 0;
    /**
     * Method for testing lock and unlock methods.
     */
    @Test
    public void whenIncrementsConcurrentlyThenUpdatesAreNotLost() {
        Lock lock = new Lock();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                lock.lock();
                this.value++;
                System.out.println(Thread.currentThread().getName() + ": " + this.value);
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                lock.lock();
                this.value++;
                System.out.println(Thread.currentThread().getName() + ": " + this.value);
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        assertThat(this.value, is(400));
    }
}