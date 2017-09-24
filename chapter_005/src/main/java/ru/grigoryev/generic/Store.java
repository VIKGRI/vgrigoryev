package ru.grigoryev.generic;
/**
 * Storage of elements.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.09.2017
 * @param <T> type of element which is stored in the storage
 */
public interface Store<T extends  Base> {
    /**
     *Adds new element in the storage.
     * @param value new element
     * @return true if operation succeeded
     */
    boolean add(T value);

    /**
     *Updates element which corresponds to the specified value  in the storage.
     * @param value element to update
     * @return true if operation succeeded
     */
    boolean update(T value);

    /**
     *Deletes element which corresponds to the specified value from the storage.
     * @param value element to delete
     * @return true if operation succeeded
     */
    boolean delete(T value);
    /**
     * Tests wether the Store contains specified value or not.
     * @param value specified value
     * @return true if array contains specified value and false otherwise
     */
    boolean  contains(T value);
}
