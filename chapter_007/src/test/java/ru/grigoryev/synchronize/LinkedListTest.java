package ru.grigoryev.synchronize;

import org.junit.Test;


import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing LinkedContainer class's methods.
 *
 * @author vgrigoryev
 * @since 09.10.2017
 */
public class LinkedListTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddMoreElementsConcurrentlyThenSizeMatches() {
        LinkedList<Integer> container = new LinkedList<>();
        synchronized (this) {
            for (int i = 1; i <= 10; i++) {
                Runnable add = new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 5; j++) {
                            container.add(j);
                        }
                    }
                };
                Thread t = new Thread(add, "Thread #" + i);
                t.start();
            }
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int result = container.size();
        int expect = 50;

        assertThat(result, is(expect));
    }
    /**
     * Testing delete() method.
     */
    @Test
    public void whenDeleteElementThenListResizes() {
        LinkedList<Integer> container = new LinkedList<>();
        synchronized (this) {
            for (int i = 1; i <= 10; i++) {
                Runnable add = new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 5; j++) {
                            container.add(j);
                        }
                    }
                };
                Thread t = new Thread(add, "Thread add#" + i);
                t.start();
            }
            for (int i = 1; i <= 10; i++) {
                Runnable add = new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 2; j++) {
                            container.delete(j);
                        }
                    }
                };
                Thread t = new Thread(add, "Thread delete#" + i);
                t.start();
            }

        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int result = container.size();
        int expect = 30;

        assertThat(result, is(expect));
    }

    /**
     * Testing get() method.
     */
    @Test
    public void whenGetElementByPositionThenValueMatches() {
        LinkedList<Integer> container = new LinkedList<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        Integer result = container.get(2);
        Integer expect = 3;

        assertThat(result, is(expect));
    }

    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        LinkedList<Integer> container = new LinkedList<>();
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);

        Iterator<Integer> it = container.iterator();
        int[] result = new int[2];
        result[0] = it.next();
        result[1] = it.next();

        int[] expect = new int[]{1, 2};
        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method when tail has reference on the previous element.
     */
    @Test
    public void  whenHasCycleThenTrue() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Node<Integer> second = list.getNode(1);
        Node<Integer> last = list.getNode(3);
        last.setNext(second);
        boolean result = list.hasCycle();

        assertThat(result, is(true));
    }
    /**
     * Testing hasNext() method when some element in the middle has reference on the previous element.
     */
    @Test
    public void  whenHasCycleInTheMiddleThenTrue() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        Node<Integer> second = list.getNode(1);
        Node<Integer> last = list.getNode(3);
        last.setNext(second);
        boolean result = list.hasCycle();

        assertThat(result, is(true));
    }
    /**
     * Testing hasCycle() method.
     */
    @Test
    public void whenHasNoCycleThenFalse() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        boolean result = list.hasCycle();

        assertThat(result, is(false));
    }
}