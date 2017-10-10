package ru.grigoryev.synchronize;

import java.util.Random;

/**
 *Class represents customer of strings.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class Customer implements Runnable {
    /**
     * Data which is used for getting information from producer.
     */
    private final BlockingQueue<String> data;

    /**
     * Constructor.
     * @param data Specified data which is used
     *             for getting information from producer.
     */
    public Customer(BlockingQueue<String> data) {
        this.data = data;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String message = data.dequeue(); message != null; message = data.dequeue()) {
            System.out.format("Message recieved by %s: %s%n", Thread.currentThread().getName(), message);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
