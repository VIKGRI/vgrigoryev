package ru.grigoryev.synchronize;

import org.junit.Test;

/**
 * Class for testing ThreadPool.
 *
 * @author vgrigoryev
 * @version 1
 * @since 10.10.2017
 */
public class ThreadPoolTest {
    /**
     * Testing add method.
     */
    @Test
    public void whenProducerWritesSomethingThenCustomerReadsIt() {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            try {
                threadPool.add(new Work());
            } catch (IllegalStateException ex) {
                System.out.println("ThreadPool is stopped explicitly");
            }
        }
    }
}