package ru.grigoryev.synchronize;

/**
 *Class represents task for executing.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class Work implements Runnable {
    @Override
    public void run() {
        System.out.println("Work is done by: " + Thread.currentThread().getName());
    }
}
