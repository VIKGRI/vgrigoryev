package ru.job4j.bank;

import java.util.Date;

/**
 * Class represent user's account.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.09.2017
 */
public class Account {
    /**
     * Account's requisites.
     */
    private Requisites requisites;
    /**
     * Amount of money account holds.
     */
    private double value;

    /**
     * Constructor with parameters.
     *
     * @param requisites Account's requisites
     * @param value      Amount of money account holds
     * @throws UnavailableAccountDataException if data of account are incorrect
     */
    public Account(Requisites requisites, double value) throws UnavailableAccountDataException {
        if (requisites != null && value >= 0) {
            this.requisites = requisites;
            this.value = value;
        } else {
            throw new UnavailableAccountDataException("Некорректные данные счета");
        }
    }

    /**
     * Getter of amount of money.
     *
     * @return amount of money
     */
    public double getValue() {
        return value;
    }
    /**
     * Setter of of amount of money.
     *
     * @param value amount of money to set
     */
    public void setValue(double value) {
        if (value >= 0) {
            this.value = value;
        }
    }
    /**
     * Getter of number of contract of this Account.
     *
     * @return number of contract
     */
    public int getAccountNum() {
        return this.requisites.getContractNum();
    }
    /**
     * This method allows to get the date of signing of contract.
     *
     * @return date of signing of contract
     */
    public Date getAccountDate() {
        return this.requisites.getDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        return requisites.equals(account.requisites);
    }

    @Override
    public int hashCode() {
        return requisites.hashCode();
    }
}
