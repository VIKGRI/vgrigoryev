package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Implementation of resizable array  which is thread safe.
 * @param <E> type of element stored in the list
 * @author vgrigoryev
 * @since 09.10.2017
 * @version 1
 */
@ThreadSafe
public class DynamicList<E> implements Iterable<E> {
    /**
     * inner array.
     */
    @GuardedBy("this")
    private Object[] objects;
    /**
     * index of first free cell which is corresponds to number of elements in this container.
     */
    @GuardedBy("this")
    private int index = 0;
    /**
     * specified capacity of the container.
     */
    @GuardedBy("this")
    private int capacity = 3;
    /**
     * Current iterator index.
     */
    @GuardedBy("this")
    private int position = 0;

    /**
     * Constructor with parameters.
     *
     * @param capacity specified capacity of the container
     */
    public DynamicList(int capacity) {
        if (capacity > this.capacity) {
            this.objects = new Object[capacity];
        }
    }

    /**
     * Default constructor.
     */
    public DynamicList() {
        this.objects = new Object[this.capacity];
    }

    /**
     * Adds specified element to the array.
     *
     * @param value value to add
     */
    public synchronized void add(E value) {
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
    public synchronized E get(int position) {
        if (this.index != 0 && position >= 0 && position < this.index) {
            return (E) objects[position];
        }
        return null;
    }

    /**
     * @return number of elements in the container
     */
    public synchronized int size() {
        return this.index;
    }

    /**
     * Returns true if the iteration has more elements.
     * @return true if the iteration has more elements
     */
    private synchronized boolean hasNextValue() {
        return this.position < this.index;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     */
    private synchronized E nextValue() {
        return (E) objects[this.position++];
    }

    @Override
    public Iterator<E> iterator() {
        this.position = 0;
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return hasNextValue();
            }

            @Override
            public E next() {
                return nextValue();
            }
        };
    }
}
