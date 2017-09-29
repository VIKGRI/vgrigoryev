package ru.grigoryev.sets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Simple hash set.
 *
 * @param <K> type of keys
 * @author vgrigoryev
 * @version 1
 * @since 29.09.2017
 */
public class SimpleHashSet<K> implements Iterable<K> {
    /**
     * inner array of objects.
     */
    private LinkedList[] objects;
    /**
     * amount of key-value pairs in the directory.
     */
    private int size;
    /**
     * specified capacity of the container.
     */
    private int capacity;
    /**
     * Determines when to resize directory.
     */
    private final double loadFactor = 0.75;

    /**
     * Constructor with parameters.
     *
     * @param capacity specified capacity of the container
     */
    public SimpleHashSet(int capacity) {
        if (capacity > 0) {
            this.size = 0;
            this.capacity = capacity;
            this.objects = new LinkedList[capacity];
        }
    }

    /**
     * Default constructor.
     */
    public SimpleHashSet() {
        this.size = 0;
        this.capacity = 10;
        this.objects = new LinkedList[this.capacity];
    }

    /**
     * Adds specified key.
     *
     * @param key specified key
     * @return true if operation succeds and false otherwise
     */
    public boolean insert(K key) {
        if (key == null) {
            return false;
        }
        boolean isSucceed = false;
        int index = this.getIndex(key);
        if (this.objects[index] != null) {
            if (!this.objects[index].contains(key)) {
                isSucceed = true;
            }
        } else {
            this.objects[index] = new LinkedList();
            isSucceed = true;
        }
        if (isSucceed) {
            this.size++;
            this.objects[index].add(key);
            if (Double.compare(loadFactor, ((double) this.size / this.capacity)) < 0) {
                this.capacity *= 2;
                LinkedList[] resized = new LinkedList[this.capacity];
                for (LinkedList list : resized) {
                    if (list != null) {
                        for (Object value : list) {
                            resized[this.getIndex((K) value)].add(value);
                        }
                    }
                }
                this.objects = resized;
            }
        }
        return isSucceed;
    }

    /**
     * Deletes key if key is found.
     *
     * @param key specified key
     * @return true if operation succeds and false otherwise
     */
    public boolean delete(K key) {
        boolean isSucceed = false;
        int index = this.getIndex(key);
        if (this.objects[index] != null && this.objects[index].contains(key)) {
            isSucceed = this.objects[index].remove(key);
            if (this.objects[index].size() == 0) {
                this.objects[index] = null;
            }
            this.size--;
        }
        return isSucceed;
    }

    /**
     * Calculates index in the array (hash-table) by the key's hashcode.
     *
     * @param key Object which represents a key in the directory
     * @return index in the array which corresponds to hashcode of key
     */
    private int getIndex(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int hashCode = key.hashCode();
        return (hashCode < 0 ? -hashCode : hashCode) % this.capacity;
    }

    /**
     * @return number of elements in the container
     */
    public int size() {
        return this.size;
    }

    /**
     * @return capacity of the container
     */
    public int capacity() {
        return capacity;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            /**
             * Current index in the hash table;
             */
            private int indexArray = 0;
            /**
             * Amount of elements which were passed.
             */
            private int traversedElements = 0;
            /**
             * Current index in the bucket.
             */
            private ListIterator it = null;

            @Override
            public boolean hasNext() {
                return traversedElements < size;
            }

            @Override
            public K next() {
                K result;
                while (true) {
                    if (this.indexArray >= capacity) {
                        throw new IndexOutOfBoundsException();
                    }
                    if (objects[this.indexArray] != null) {
                        if (it == null) {
                            it = objects[this.indexArray].listIterator();
                        }
                        if (it.hasNext()) {
                            result = (K) it.next();
                            traversedElements++;
                            break;
                        } else {
                            this.indexArray++;
                            it = null;
                        }
                    } else {
                        this.indexArray++;
                    }
                }
                return result;
            }
        };
    }

    @Override
    public String toString() {
        return "SimpleHashSet{"
                + "objects=" + Arrays.toString(objects)
                + ", size=" + size + '}';
    }
}
