package ru.grigoryev.lists;

/**
 * Implementation of stack (LIFO).
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 25.09.2017
 */
public class SimpleStack<E> {
    /**
     *Inner container of elements.
     */
    private LinkedContainer<E> objects = new LinkedContainer<>();
    /**
     *Points to the last added element. If it is -1 the stack is empty.
     */
    private int cursor = -1;
    /**
     * Returns the last input.
     *
     * @return value on the top of stack or null if it is empty
     */
    public E poll() {
        E result = null;
        if (cursor != -1) {
            result = objects.get(cursor);
            objects.delete(cursor);
            cursor--;
        }
        return result;
    }

    /**
     * Puts value on the top of the stack.
     * @param value specified value to add
     */
    public void push(E value) {
        objects.add(value);
        cursor++;
    }
    /**
     * @return number of elements in the stack
     */
    public int size() {
        return objects.size();
    }
}
