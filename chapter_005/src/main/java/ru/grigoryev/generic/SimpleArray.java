package ru.grigoryev.generic;

/**
 * SimpleArray represents a wrapper for array.
 *
 * @param <E> type of elements stored in the array.
 * @author vgrigoryev
 * @version 1
 * @since 22.09.2017
 */
public class SimpleArray<E> {
    /**
     * inner array.
     */
    private Object[] objects;
    /**
     * index of first free cell.
     */
    private int index = 0;

    /**
     * Constructor.
     *
     * @param size specified size of an array
     */
    public SimpleArray(int size) {
        if (size >= 0) {
            this.objects = new Object[size];
        }
    }

    /**
     * Adds specified element to the array.
     *
     * @param value value to add
     * @return true if addition succeeds and false otherwise
     */
    public boolean add(E value) {
        boolean isAdded = false;
        if (index < this.objects.length) {
            objects[index++] = value;
            isAdded = true;
        }
        return isAdded;
    }

    /**
     * Deletes element in the specified position.
     *
     * @param position specified position of the element
     * @return true if deletion succeeds and false otherwise
     */
    public boolean delete(int position) {
        boolean isDeleted = false;
        if (this.index != 0 && position >= 0 && position < this.index) {
            for (int i = position; i < this.index - 1; i++) {
                objects[i] = objects[i + 1];
            }
            this.index--;
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * Updatess element in the specified position with the specified value.
     * @param position specified position of the element
     * @param value    specified value
     * @return true if operation succeeds and false otherwise
     */
    public boolean update(int position, E value) {
        boolean isUpdated = false;
        if (this.index != 0 && position >= 0 && position < this.index) {
            objects[position] = value;
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Gets element in the specified position.
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
     * Tests wether the array contains specified value or not.
     * @param value specified value
     * @return true if array contains specified value and false otherwise
     */
    public boolean contains(E value) {
        boolean isExist = false;
        for (int i = 0; i < this.index; i++) {
            if (value.equals(objects[i])) {
                isExist = true;
            }
        }
        return isExist;
    }
}
