package ru.grigoryev.synchronize;

/**
 * Class provides increment operation which is thread safe.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.10.2017
 */
public class User {
    /**
     * User's id.
     */
    private int id;
    /**
     * User'a account.
     */
    private int amount;

    /**
     * Constructor.
     * @param id User's id
     * @param amount User'a account
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * Gets user's id.
     * @return user's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets current state of user's account.
     * @return amount of units in the user's account
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets new amount of units in the user's account.
     * @param amount new amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
