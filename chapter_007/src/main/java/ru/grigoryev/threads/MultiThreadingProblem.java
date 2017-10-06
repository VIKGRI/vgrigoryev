package ru.grigoryev.threads;

/**
 *Class for demonstrating problems of concurrent programming.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class MultiThreadingProblem {
    /**
     * Class which represents action of increment.
     */
    private class IncrementAction implements Runnable {
        /**
         * value to modify.
         */
        private Value val;

        /**
         * Constructor.
         *
         * @param val value to modify
         */
        IncrementAction(Value val) {
            this.val = val;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                this.val.incrementValue();
            }
        }
    }

    /**
     * Demonstrates problems which occurs in concurrent programs.
     * @param value value to modify
     */
    public void demonstrate(Value value) {
        Thread t1 = new Thread(new IncrementAction(value));
        Thread t2 = new Thread(new IncrementAction(value));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

