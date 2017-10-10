package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.List;

/**
 *Class provides blocking queue.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 *@param <E> type of element stored in the queue
 */
@ThreadSafe
public class BlockingQueue<E> {
    /**
     * Inner queue implemented as linked list.
     */
    @GuardedBy("this")
    private List<E> queue = new java.util.LinkedList<>();
    /**
     * Limit size of queue.
     */
    @GuardedBy("this")
    private int  limit = 10;

    /**
     * Constructor.
     * @param limit Limit size of queue
     */
    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * Puts element in the queue if it is not full
     * and blocks the executing thread otherwise.
     * @param element specified element to put
     */
    public synchronized void enqueue(E element) {
        while (this.queue.size() == this.limit) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        System.out.println("Producer " + Thread.currentThread().getName() + " is putting");
        this.queue.add(element);
    }

    /**
     * Gets the element and removes it from queue
     * if it is present and blocks the executing thread otherwise.
     * @return removed element
     */
    public synchronized E dequeue() {
        while (this.queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (this.queue.size() == this.limit) {
            notifyAll();
        }
        System.out.println("Customer " + Thread.currentThread().getName() + " is taking");
        return this.queue.remove(0);
    }
}
