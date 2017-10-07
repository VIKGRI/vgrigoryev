package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.grigoryev.threads.Value;

/**
 * Class provides increment operation which is thread safe.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.10.2017
 */
@ThreadSafe
public class Count implements Runnable {
    /**
     * wrapped primitive integer value.
     */
    @GuardedBy("this")
    private Value value;

    /**
     * Constructor.
     *
     * @param value initial value
     */
    public Count(Value value) {
        this.value = value;
    }

    /**
     * Performs increment operation in atomically.
     */
    public synchronized void increment() {
        this.value.incrementValue();
    }

    /**
     * Value's getter.
     *
     * @return inner value
     */
    public synchronized int get() {
        return this.value.getValue();
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            increment();
        }
    }
}
