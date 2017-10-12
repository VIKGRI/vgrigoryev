package ru.grigoryev.synchronize;

import java.util.Random;

/**
 *Class represents producer of strings.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class Producer implements Runnable {
    /**
     * Number of iterations of the loop in the run method.
     */
    private final int iterationNum;
    /**
     * Data which is used for supplying information for customer.
     */
    private final BlockingQueue<String> data;
    /**
     * Creator of random strings.
     */
    private final RanomStringCreator stringCreator;

    /**
     * Constructor.
     * @param data Specified data which is used
     *             for supplying information for customer.
     * @param iterationNum Number of iterations.
     */
    public Producer(BlockingQueue<String> data, int iterationNum) {
        this.data = data;
        this.stringCreator = new RanomStringCreator();
        this.iterationNum = iterationNum;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < iterationNum; i++) {
                data.enqueue(stringCreator.createRandomString());
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        data.enqueue(null);
    }
}
