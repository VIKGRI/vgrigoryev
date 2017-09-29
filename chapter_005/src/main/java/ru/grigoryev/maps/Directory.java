package ru.grigoryev.maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Directory of keys and values which are a associated with a key.
 *
 * @param <K> type of keys
 * @param <V> type of values
 * @author vgrigoryev
 * @version 1
 * @since 28.09.2017
 */
public class Directory<K, V> implements Iterable<Pair<K, V>> {
    /**
     * inner array of key-value pairs.
     */
    private Pair[] pairs;
    /**
     * amount of key-value pairs in the directory.
     */
    private int size = 0;
    /**
     * specified capacity of the container.
     */
    private int capacity = 5;
    /**
     * Determines when to resize directory.
     */
    private final double loadFactor = 0.75;

    /**
     * Constructor with parameters.
     *
     * @param capacity specified capacity of the container
     */
    public Directory(int capacity) {
        if (capacity > this.capacity) {
            this.pairs = new Pair[capacity];
        }
    }

    /**
     * Default constructor.
     */
    public Directory() {
        this.pairs = new Pair[this.capacity];
    }

    /**
     * Adds specified key with associated value.
     *
     * @param key specified key
     * @param value value to add
     * @return true if operation succeds and false otherwise
     */
    public boolean insert(K key, V value) {
        boolean isSucceed = false;
        int index = this.getIndex(key);
        if (this.pairs[index] != null) {
            if (Objects.equals(key, this.pairs[index].getKey())) {
                isSucceed = true;
            }
        } else {
            this.pairs[index] = new Pair<K, V>(key, null);
            isSucceed = true;
            this.size++;
        }
        if (isSucceed) {
            this.pairs[index].setValue(value);
            if (Double.compare(loadFactor, ((double) this.size / this.capacity)) < 0) {
                this.capacity *= 2;
                Pair[] resized = new Pair[this.capacity];
                for (Pair<?, ?> pair : this.pairs) {
                    if (pair != null) {
                        resized[this.getIndex((K) pair.getKey())] = pair;
                    }
                }
                this.pairs = resized;
            }
        }
        return isSucceed;
    }

    /**
     * Deletes pair key-value if key is found.
     *
     * @param key specified key
     * @return true if operation succeds and false otherwise
     */
    public boolean delete(K key) {
        boolean isSucceed = false;
        int index = this.getIndex(key);
        if (this.pairs[index] != null && Objects.equals(key, this.pairs[index].getKey())) {
            isSucceed = true;
            this.pairs[index] = null;
            this.size--;
        }
        return isSucceed;
    }

    /**
     * Gets value by specified key if it exists.
     *
     * @param key specified key which is associated with value.
     * @return value associated with specified key if it exists or null otherwise.
     */
    V get(K key) {
        int index = this.getIndex(key);
        V value = null;
        if (this.pairs[index] != null
                && Objects.equals(key, this.pairs[index].getKey())) {
            value = (V) this.pairs[index].getValue();
        }
        return value;
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
    public String toString() {
        return "Directory{"
                +
                "pairs=" + Arrays.toString(pairs)
                + '}';
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new Iterator<Pair<K, V>>() {
            /**
             * Current index;
             */
            private int index = 0;
            /**
             * Amount of elements which were passed.
             */
            private int traversedElements = 0;

            @Override
            public boolean hasNext() {
                return traversedElements < size;
            }

            @Override
            public Pair<K, V> next() {
                for (; this.index < capacity; this.index++) {
                    if (pairs[this.index] != null) {
                        traversedElements++;
                        break;
                    }
                }
                return (Pair<K, V>) pairs[this.index++];
            }
        };
    }
}
