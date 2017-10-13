package ru.grigoryev.synchronize;

import org.junit.Test;

import java.util.Random;

/**
 * Class for testing NonBlock class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 13.10.2017
 */
public class NonBlockTest {
    /**
     * Non-blocking cache.
     */
    private NonBlock cache;
    /**
     * Random object for sleeping time.
     */
    private Random random = new Random();
    /**
     * Task for updating threads.
     */
    private Runnable taskUpdate = () -> {
        /**
         * .
         */
        for (int i = 0; i < 100; i++) {
            cache.update((int) (Math.random() * 30), RandomStringCreator.createRandomString());
            try {
                    Thread.sleep(random.nextInt(500));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };
    /**
     * Task for deleting threads.
     */
    private Runnable taskDelete = () -> {
        /**
         * .
         */
        for (int i = 0; i < 10; i++) {
            cache.delete((int) (Math.random() * 30));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };
    /**
     * Testing NonBlock's methods.
     */
    @Test
    public void whenUpdatingModelsThenThreadsTryToRewriteData() {
        cache = new NonBlock();
        for (int i = 0; i < 30; i++) {
            cache.add(new Model(i, RandomStringCreator.createRandomString(), 0));
        }
        Thread updateFirst = new Thread(taskUpdate);
        Thread updateSecond = new Thread(taskUpdate);
        Thread updateThird = new Thread(taskUpdate);
        Thread update4 = new Thread(taskUpdate);
        Thread update5 = new Thread(taskUpdate);
        Thread update6 = new Thread(taskUpdate);

        Thread deleteFirst = new Thread(taskDelete);
        updateFirst.start();
        updateSecond.start();
        updateThird.start();
        update4.start();
        update5.start();
        update6.start();
        deleteFirst.start();
        try {
            updateFirst.join();
            updateSecond.join();
            updateThird.join();
            update4.join();
            update5.join();
            update6.join();
            deleteFirst.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Program is over");
    }
}