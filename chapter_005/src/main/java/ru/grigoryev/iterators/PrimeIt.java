package ru.grigoryev.iterators;

import java.util.Iterator;

/**
 * Iterator for array of elements of int type which returns prime numbers.
 *
 * @author vgrigoryev
 * @version 1
 * @since 21.09.2017
 */

public class PrimeIt implements Iterator {
    /**
     * Inner array.
     */
    private final int[] values;
    /**
     * First index in the array.
     */
    private int index = 0;

    /**
     * Constructor.
     *
     * @param values array
     */
    public PrimeIt(final int[] values) {
        this.values = values;
    }

    /**
     * Method identifies whether the value prime or not.
     *
     * @param value to check
     * @return true if value is prime and false otherwise
     */
    private boolean isPrime(int value) {
        boolean isPrime = true;
        if (value > 1) {
            int limit = (int) Math.sqrt(value);
            for (int i = 2; i <= limit; i++) {
                if (value % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        } else {
            isPrime = false;
        }
        return isPrime;
    }

    @Override
    public boolean hasNext() {
        boolean isNextPrimePresent = false;
        for (int i = this.index; i < values.length; i++) {
            if (this.isPrime(values[i])) {
                isNextPrimePresent = true;
                break;
            }
        }
        return isNextPrimePresent;
    }

    /*
    When there are no prime numbers, then 0 returns.
     */
    @Override
    public Object next() {
        int isNextPrime = 0;
        for (; this.index < values.length; this.index++) {
            if (this.isPrime(values[this.index])) {
                isNextPrime = values[this.index++];
                break;
            }
        }
        return isNextPrime;
    }
}
