package ru.grigoryev.sets;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Implementation of resizable set.
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 26.09.2017
 */
public class SimpleSet<E> implements Iterable<E> {
    /**
     * inner array.
     */
    private Object[] objects;
    /**
     * index of first free cell which is corresponds to number of elements in this container.
     */
    private int index = 0;
    /**
     * specified capacity of the container.
     */
    private int capacity = 3;

    /**
     * Constructor.
     *
     * @param capacity specified capacity of the set
     */
    public SimpleSet(int capacity) {
        if (capacity > this.capacity) {
            this.objects = new Object[capacity];
        }
    }

    /**
     * Default constructor.
     */
    public SimpleSet() {
        this.objects = new Object[this.capacity];
    }

    /**
     * Adds specified element to the list.
     *
     * @param value value to add
     */
    public void add(E value) {
        boolean doesContain = false;
        for (int i = 0; i < this.index; i++) {
            if (this.objects[i].equals(value)) {
                doesContain = true;
                break;
            }
        }
        if (!doesContain) {
            if (this.index >= this.capacity) {
                this.capacity *= 2;
                this.objects = Arrays.copyOf(this.objects, this.capacity);
            }
            this.objects[this.index++] = value;
        }
    }

    /**
     * @return number of elements in the container
     */
    public int size() {
        return this.index;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * Current index.
             */
            private int position = 0;

            @Override
            public boolean hasNext() {
                return this.position < index;
            }

            @Override
            public E next() {
                return (E) objects[this.position++];
            }
        };
    }
}
