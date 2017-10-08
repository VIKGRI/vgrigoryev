package ru.grigoryev.synchronize;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing thread safe UserStorage class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 08.10.2017
 */

public class UserStorageTest {
    /**
     * Initial amount of units each user has.
     */
    public static final int USER_AMOUNT = 100;
    /**
     * Method for testing transfer method.
     */
    @Test
    public void whenPerformManyParallelOperationsThenCommonStateDoesntChange() {
        UserStorage storage = new UserStorage();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT);
            storage.add(user);
        }

        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                Runnable transfer = () -> {
                    int fromUser;
                    int toUser;
                    for (int j = 0; j < 5; j++) {
                        fromUser = (int) (50 * Math.random());
                        toUser = (int) (50 * Math.random());
                        int amount = (int) (USER_AMOUNT * Math.random());
                        storage.transfer(fromUser, toUser, amount);
                    }
                };
                Thread t = new Thread(transfer, "Thread #" + i);
                t.start();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int expected = storage.getTotalAmountByField();
        int result = storage.getTotalAmountByIterate();
        assertThat(result, is(expected));
    }
    /**
     * Method for testing add method.
     */
    @Test
    public void whenAddSameUsersThenOperationFails() {
        UserStorage storage = new UserStorage();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT);
            storage.add(user);
        }
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT * 2);
            storage.add(user);
        }
        int result = storage.getTotalAmountByIterate();
        int expected = 50 * USER_AMOUNT;
        assertThat(result, is(expected));
    }
    /**
     * Method for testing delete method.
     */
    @Test
    public void whenDeleteUsersThenAmountZero() {
        UserStorage storage = new UserStorage();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT);
            storage.add(user);
        }
        int beforeDelete = storage.getTotalAmountByIterate();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, 0);
            storage.delete(user);
        }

        boolean result = beforeDelete == 50 * USER_AMOUNT && storage.getTotalAmountByField() == 0;
        assertThat(result, is(true));
    }
    /**
     * Method for testing update method.
     */
    @Test
    public void whenUpdateEachUserDivideByTwoThenCommonAmountDividedByTwo() {
        UserStorage storage = new UserStorage();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT * 2);
            storage.add(user);
        }
        int beforeUpdate = storage.getTotalAmountByIterate();
        for (int i = 1; i <= 50; i++) {
            User user = new User(i, USER_AMOUNT);
            storage.update(user);
        }

        boolean result = beforeUpdate == 50 * 2 * USER_AMOUNT && storage.getTotalAmountByField() == 50 * USER_AMOUNT;
        assertThat(result, is(true));
    }
}