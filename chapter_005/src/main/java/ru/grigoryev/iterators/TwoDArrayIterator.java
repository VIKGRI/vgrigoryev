package ru.grigoryev.iterators;

import java.util.Iterator;

/**
 * Iterator for 2D array of elements of T type.
 * @author vgrigoryev
 * @since 21.09.2017
 * @version 1
 * @param <T> element of the inner array
 */
public class TwoDArrayIterator<T> implements Iterator<T> {
    /**
     * Inner array.
     */
    private final T[][] inner;
    /**
     * First index in 2D array.
     */
    private int iIndex = 0;
    /**
     *Second index in 2D array.
     */
    private int jIndex = 0;

    /**
     *Constructor.
     * @param inner array
     */
    public TwoDArrayIterator(final T[][] inner) {

        this.inner = inner;
    }

    @Override
    public boolean hasNext() {
        return iIndex < inner.length && jIndex < inner[iIndex].length;
    }

    @Override
    public T next() {
        int i = iIndex;
        int j = jIndex;
        jIndex = ++jIndex % inner[iIndex].length;
        if (jIndex == 0) {
            iIndex++;
        }
        return inner[i][j];
    }
}
