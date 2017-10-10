package ru.grigoryev.synchronize;

import org.junit.Test;
/**
 * Class for testing Producer and Customer interchange.
 *
 * @author vgrigoryev
 * @version 1
 * @since 10.10.2017
 */
public class ProducerCustomerTest {
    /**
     * Testing roducer and Customer interchange by BlockingQueue' methods.
     */
    @Test
    public void whenProducerWritesSomethingThenCustomerReadsIt() {
        BlockingQueue<String> dataBase = new BlockingQueue<>(10);
        Thread producer = new Thread(new Producer(dataBase));
        Thread customer = new Thread(new Customer(dataBase));
        producer.start();
        customer.start();
        try {
            producer.join();
            customer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Transaction is over");
    }
}