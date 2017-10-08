package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A Storage of users.
 *
 * @author vgrigoryev
 * @version 1.0
 */
@ThreadSafe
public class UserStorage {
    /**
     * Inner container of users.
     */
    @GuardedBy("storageLock")
    private final Map<Integer, User> users;
    /**
     * Common amount of units all users have in the storage.
     */
    @GuardedBy("storageLock")
    private int commonAmount;
    /**
     * lock for making operations thread safe.
     */
    private Lock storageLock;

    /**
     * Constructs new storage.
     */
    public UserStorage() {
        this.users = new LinkedHashMap<>();
        this.commonAmount = 0;
        this.storageLock = new ReentrantLock();
    }

    /**
     * Adds the specified user to this storage if he is not already present.
     * @param user user to be added to this storage
     * @return true if this storage did not already contain the specified user
     */
    public boolean add(User user) {
        this.storageLock.lock();
        try {
            boolean isAdded = this.users.containsKey(user.getId());
            if (!isAdded) {
                this.users.put(user.getId(), user);
                this.commonAmount += user.getAmount();
                isAdded = true;
            }
            return  isAdded;
        } finally {
            this.storageLock.unlock();
        }
    }

    /**
     * Removes the specified user from this storage if he is present.
     * @param user user to be removed to this storage
     * @return true if the storage contained the specified user
     */
    public boolean delete(User user) {
        this.storageLock.lock();
        try {
            boolean isRemoved = this.users.containsKey(user.getId());
            if (isRemoved) {
                this.commonAmount -= this.users.get(user.getId()).getAmount();
                this.users.remove(user.getId());
            }
            return isRemoved;
        } finally {
            this.storageLock.unlock();
        }
    }

    /**
     * Updates the specified user in this storage if he is present.
     * @param user user to be updated to this storage
     * @return true if the storage contained the specified user
     */
    public boolean update(User user) {
        this.storageLock.lock();
        try {
            boolean isUpdated = this.users.containsKey(user.getId());
            if (isUpdated) {
                this.commonAmount -= this.users.get(user.getId()).getAmount();
                this.commonAmount += user.getAmount();
                this.users.put(user.getId(), user);
            }
            return isUpdated;
        } finally {
            this.storageLock.unlock();
        }
    }

    /**
     * Gets the number of users in the storage.
     * @return the number of users
     */
    public int size() {
        this.storageLock.lock();
        try {
            return this.users.size();
        } finally {
            this.storageLock.unlock();
        }
    }

    /**
     * Returns a total amount of units in the storage counted by iteration of all users.
     * Operation is used for testing.
     * @return total amount of units in the storage
     */
    public int getTotalAmountByIterate() {
        this.storageLock.lock();
        try {
            int sum = 0;
            for (Map.Entry<Integer, User> user: this.users.entrySet()) {
                sum += user.getValue().getAmount();
            }
            return sum;
        } finally {
            this.storageLock.unlock();
        }
    }
    /**
     * Returns a total amount of units in the storage which is stored in class field.
     * Operation is used for testing.
     * @return total amount of units in the storage
     */
    public int getTotalAmountByField() {
        this.storageLock.lock();
        try {
            return this.commonAmount;
        } finally {
            this.storageLock.unlock();
        }
    }

    /**
     * Transfers specified amount of units from one user to another.
     * @param fromId User from whom transfer
     * @param toId User to whom transfer
     * @param amount amount of units to transfer
     * @return true if operation succeds and false otherwise
     */
    public boolean transfer(int fromId, int toId, int amount) {
        this.storageLock.lock();
        try {
            boolean isTransfered = this.users.containsKey(fromId)
                    && this.users.containsKey(toId);
            if (isTransfered) {
                if (amount <= this.users.get(fromId).getAmount()) {
                    this.update(new User(fromId, this.users.get(fromId).getAmount() - amount));
                    this.update(new User(toId, this.users.get(toId).getAmount() + amount));
                    System.out.println(Thread.currentThread() + ": Transfer from "
                    + fromId + " to " + toId + " amount: " + amount);
                } else {
                    isTransfered = false;
                }
            }
            return isTransfered;
        } finally {
            this.storageLock.unlock();
        }
    }
}
