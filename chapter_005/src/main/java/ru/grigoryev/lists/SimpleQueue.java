package ru.grigoryev.lists;

/**
 * Implementation of queue (FIFO).
 *
 * @param <E> type of element stored in the container
 * @author vgrigoryev
 * @version 1
 * @since 25.09.2017
 */
public class SimpleQueue<E> {
    /**
     *Inner container of elements.
     */
    private LinkedContainer<E> objects = new LinkedContainer<>();
    /**
     *Points to the first added element.
     */
    private int head = 0;
    /**
     *Points to the first free cell after last added element.
     */
    private int tail = head;
    /**
     * Returns the first input.
     *
     * @return value first value in thequeue or null if it is empty
     */
    public E poll() {
        E result = null;
        if (this.head != this.tail) {
            result = this.objects.get(this.head);
            objects.delete(this.head);
            this.tail--;
        }
        return result;
    }

    /**
     * Puts value on the tail of the queue.
     * @param value specified value to add
     */
    public void push(E value) {
        this.objects.add(value);
        this.tail++;
    }
    /**
     * @return number of elements in the queue
     */
    public int size() {
        return this.objects.size();
    }
}
