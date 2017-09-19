package ru.job4j.bank;

import java.util.Date;

/**
 * Class represent requisites of user's account in the bank.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.09.2017
 */
public class Requisites {
    /**
     * This field represents the number of contract.
     */
    private int contractNum;
    /**
     * This field represents date signing of contract.
     */
    private Date date;

    /**
     * Constructor with parameters.
     *
     * @param contractNum Number of contrast
     */
    public Requisites(int contractNum) {
        if (contractNum > 0) {
            this.contractNum = contractNum;
        }
        /*
        *The date of creation is automatically set to current time.
		 */
        this.date = new Date(System.currentTimeMillis());
    }

    /**
     * Getter of date of creation.
     *
     * @return date of creation
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter of date of creation.
     *
     * @param date date to set
     */
    public void setDate(Date date) {
        if (date != null) {
            this.date = date;
        }
    }

    /**
     * Getter of number of contract.
     *
     * @return number of contract
     */
    public int getContractNum() {
        return contractNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Requisites that = (Requisites) o;

        return contractNum == that.contractNum;
    }

    @Override
    public int hashCode() {
        return contractNum;
    }

    /**
     * Setter of date of creation.

     * @param contractNum date to set

     */
    public void setContractNum(int contractNum) {
        if (contractNum > 0) {
            this.contractNum = contractNum;
        }
    }
}