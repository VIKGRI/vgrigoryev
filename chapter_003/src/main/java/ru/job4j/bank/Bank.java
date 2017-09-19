package ru.job4j.bank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Class represent bank which holds user's accounts and provides operations on it.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.09.2017
 */
public class Bank {
    /**
     * This field represent the data about users and their accounts.
     */
    private Map<User, List<Account>> userData;

    /**
     * Default Constructor.
     */
    public Bank() {
        this.userData = new HashMap<User, List<Account>>();
    }

    /**
     * Adds new user to the bank or do nothing if this user exists.
     *
     * @param user new user to add to the bank
     */
    public void addUser(User user) {
        if (!this.userData.containsKey(user)) {
            this.userData.put(user, new LinkedList<Account>());
        }
    }

    /**
     * Deletes user from the bank or do nothing if this user doesn't exist.
     *
     * @param user user to delete from the bank
     */
    public void deleteUser(User user) {
        this.userData.remove(user);
    }

    /**
     * Adds new account to the user if this user exists and if this account doesn't exist.
     *
     * @param user    user to whom to add the new account
     * @param account new account
     * @throws NoSuchUserException             if such user is not found
     * @throws RewriteExistingAccountException if such account has already been created
     */
    public void addAccountToUser(User user, Account account) throws NoSuchUserException, RewriteExistingAccountException {
        List<Account> userAccounts = this.userData.get(user);
        if (userAccounts == null) {
            throw new NoSuchUserException("Пользователь не найден");
        }
        for (Account value : userAccounts) {
            if (value.equals(account)) {
                throw new RewriteExistingAccountException("Попытка добавить существующий счет");
            }
        }
        userAccounts.add(account);
    }

    /**
     * @param user    user from whom to delete account
     * @param account account to delete
     * @throws NoSuchUserException    if such user is not found
     * @throws NoSuchAccountException if such account is not found
     */
    public void deleteAccountFromUser(User user, Account account) throws NoSuchUserException, NoSuchAccountException {
        List<Account> userAccounts = this.userData.get(user);
        if (userAccounts == null) {
            throw new NoSuchUserException("Пользователь не найден");
        }
        ListIterator<Account> lit = userAccounts.listIterator();
        boolean isFound = false;
        for (; lit.hasNext();) {
            if (lit.next().equals(account)) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            userAccounts.remove(lit.previous());
        } else {
            throw new NoSuchAccountException("Счет не найден");
        }
    }

    /**
     * @param user user whose accounts are returned
     * @return list of all accounts of specified user
     * @throws NoSuchUserException if such user is not found
     */
    public List<Account> getUserAccounts(User user) throws NoSuchUserException {
        List<Account> userAccounts = this.userData.get(user);
        if (userAccounts == null) {
            throw new NoSuchUserException("Пользователь не найден");
        }
        return userAccounts;
    }

    /**
     * Transfers money from one user's account to another user's account. The user can the same.
     * @param srcUser User from whom to transfer money
     * @param srcAccount Account from which to transfer money
     * @param dstUser User to whom to transfer money
     * @param dstAccount Account to which to transfer money
     * @param amount Amount of money to transfer
     * @return true if operation succeeds
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean isOperationSucceed = true;
        List<Account> srcUserAccounts = this.userData.get(srcUser);
        if (srcUserAccounts == null) {
            isOperationSucceed = false;
        }
        List<Account> dstUserAccounts = this.userData.get(dstUser);
        if (dstUserAccounts == null) {
            isOperationSucceed = false;
        }
        if (isOperationSucceed) {
            ListIterator<Account> srcLit = srcUserAccounts.listIterator();
            isOperationSucceed = false;
            for (; srcLit.hasNext();) {
                if (srcLit.next().equals(srcAccount)) {
                    isOperationSucceed = true;
                    srcAccount = srcLit.previous();
                    break;
                }
            }
        }
        if (isOperationSucceed) {
            ListIterator<Account> dstLit = dstUserAccounts.listIterator();
            isOperationSucceed = false;
            for (; dstLit.hasNext();) {
                if (dstLit.next().equals(dstAccount)) {
                    isOperationSucceed = true;
                    dstAccount = dstLit.previous();
                    break;
                }
            }
        }
        if (isOperationSucceed) {
            if (srcAccount.getValue() >= amount) {
                srcAccount.setValue(srcAccount.getValue() - amount);
                dstAccount.setValue(dstAccount.getValue() + amount);
            } else {
                isOperationSucceed = false;
            }
        }
        return isOperationSucceed;
    }

    /**
     *Return all user in the bank.
     * @return all user in the bank
     */
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>(this.userData.keySet());
        return users;
    }
}