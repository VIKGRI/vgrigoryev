package ru.grigoryev.iterators;
import java.util.Iterator;

/**
 * Iterator for array of elements of int type which returns even numbers.
 * @author vgrigoryev
 * @since 21.09.2017
 * @version 1
 */
public class EvenArrayIterator implements  Iterator {
    /**
     * Inner array.
     */
    private  final int[] values;
    /**
     * First index in the array.
     */
    private int index = 0;
    /**
     *Constructor.
     * @param values array
     */
    public EvenArrayIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return this.index < values.length;
    }
/*
When there are no even numbers, then 1 returns.
 */
    @Override
    public Object next() {
        int isNextEvenPresent = 1;
        for (; this.index < values.length; this.index++) {
            if (values[this.index] % 2 == 0) {
                isNextEvenPresent = values[this.index++];
                break;
            }
        }
        return isNextEvenPresent;
    }
}
