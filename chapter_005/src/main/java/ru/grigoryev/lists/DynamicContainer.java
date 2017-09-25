package ru.grigoryev.lists;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Implementation of resizable array.
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @since 25.09.2017
 * @version 1
 */
public class DynamicContainer<E> implements Iterable<E> {
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
     * Constructor with parameters.
     *
     * @param capacity specified capacity of the container
     */
    public DynamicContainer(int capacity) {
        if (capacity > this.capacity) {
            this.objects = new Object[capacity];
        }
    }

    /**
     * Default constructor.
     */
    public DynamicContainer() {
        this.objects = new Object[this.capacity];
    }

    /**
     * Adds specified element to the array.
     *
     * @param value value to add
     */
    public void add(E value) {
        if (this.index >= this.capacity) {
            this.capacity *= 2;
            this.objects = Arrays.copyOf(this.objects, this.capacity);
        }
        this.objects[this.index++] = value;
    }


    /**
     * Gets element in the specified position.
     *
     * @param position specified position of the element
     * @return the element in specified position or null if it doesn't exist
     */
    public E get(int position) {
        if (this.index != 0 && position >= 0 && position < this.index) {
            return (E) objects[position];
        }
        return null;
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
